package com.borelanjo.behavior;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

import java.awt.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class TrackerBehavior implements Behavior {

    private final AdvancedRobot robot;

    private int count = 0; // Keeps track of how long we've

    // been searching for our target
    private double gunTurnAmt; // How much to turn our gun when searching

    private String trackName; // Name of the robot we're currently tracking

    public TrackerBehavior(AdvancedRobot robot) {
        this.robot = robot;
        this.trackName = null; // Initialize to not tracking anyone
        this.gunTurnAmt = 10; // Initialize gunTurn to 10
    }

    /**
     * run:  Tracker's main run function
     */
    public void run() {
        robot.setAdjustGunForRobotTurn(true); // Keep the gun still when we turn

        robot.setGunColor(new Color(50, 50, 20));

        // turn the Gun (looks for enemy)
        robot.turnGunRight(gunTurnAmt);
        // Keep track of how long we've been looking
        count++;
        // If we've haven't seen our target for 2 turns, look left
        if (count > 2) {
            gunTurnAmt = -10;
        }
        // If we still haven't seen our target for 5 turns, look right
        if (count > 5) {
            gunTurnAmt = 10;
        }
        // If we *still* haven't seen our target after 10 turns, find another target
        if (count > 11) {
            trackName = null;
        }
    }

    /**
     * onScannedRobot:  Here's the good stuff
     */
    public void onScannedRobot(ScannedRobotEvent e) {

        // If we have a target, and this isn't it, return immediately
        // so we can get more ScannedRobotEvents.
        if (trackName != null && !e.getName().equals(trackName)) {
            return;
        }

        // If we don't have a target, well, now we do!
        if (trackName == null) {
            trackName = e.getName();
            robot.out.println("Tracking " + trackName);
        }
        // This is our target.  Reset count (see the run method)
        count = 0;
        // If our target is too far away, turn and move toward it.
        if (e.getDistance() > 150) {
            gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (robot.getHeading() - robot.getRadarHeading()));

            robot.turnGunRight(gunTurnAmt); // Try changing these to setTurnGunRight,
            robot.turnRight(e.getBearing()); // and see how much Tracker improves...
            // (you'll have to make Tracker an AdvancedRobot)
            robot.ahead(e.getDistance() - 140);
            return;
        }

        // Our target is close.
        gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (robot.getHeading() - robot.getRadarHeading()));
        robot.turnGunRight(gunTurnAmt);
        robot.fire(3);

        // Our target is too close!  Back up.
        if (e.getDistance() < 100) {
            if (e.getBearing() > -90 && e.getBearing() <= 90) {
                robot.back(40);
            } else {
                robot.ahead(40);
            }
        }
        robot.scan();
    }

    /**
     * onHitRobot:  Set him as our new target
     */
    public void onHitRobot(HitRobotEvent e) {
        // Only print if he's not already our target.
        if (trackName != null && !trackName.equals(e.getName())) {
            robot.out.println("Tracking " + e.getName() + " due to collision");
        }
        // Set the target
        trackName = e.getName();
        // Back up a bit.
        // Note:  We won't get scan events while we're doing this!
        // An AdvancedRobot might use setBack(); execute();
        gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (robot.getHeading() - robot.getRadarHeading()));
        robot.turnGunRight(gunTurnAmt);
        robot.fire(3);
        robot.back(50);
    }

    /**
     * onWin:  Do a victory dance
     */
    public void onWin(WinEvent e) {
        for (int i = 0; i < 50; i++) {
            robot.turnRight(30);
            robot.turnLeft(30);
        }
    }
}
