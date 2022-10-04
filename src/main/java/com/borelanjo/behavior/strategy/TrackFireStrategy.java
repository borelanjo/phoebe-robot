package com.borelanjo.behavior.strategy;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

import java.awt.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class TrackFireStrategy implements Strategy {

    private final AdvancedRobot robot;

    public TrackFireStrategy(AdvancedRobot robot) {
        this.robot = robot;
    }

    @Override
    public void run() {
        robot.setGunColor(Color.pink);
        robot.setAdjustGunForRobotTurn(false); // Keep the gun still when we turn

        robot.turnGunRight(10); // Scans automatically
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        // Calculate exact location of the robot
        double absoluteBearing = robot.getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - robot.getGunHeading());

        // If it's close enough, fire!
        if (Math.abs(bearingFromGun) <= 3) {
            robot.turnGunRight(bearingFromGun);
            // We check gun heat here, because calling fire()
            // uses a turn, which could cause us to lose track
            // of the other robot.
            if (robot.getGunHeat() == 0) {
                robot.fire(Math.min(3 - Math.abs(bearingFromGun), robot.getEnergy() - .1));
            }
        } // otherwise just set the gun to turn.
        // Note:  This will have no effect until we call scan()
        else {
            robot.turnGunRight(bearingFromGun);
        }
        // Generates another scan event if we see a robot.
        // We only need to call this if the gun (and therefore radar)
        // are not turning.  Otherwise, scan is called automatically.
        if (bearingFromGun == 0) {
            robot.scan();
        }
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {

    }

    @Override
    public void onWin(WinEvent e) {
        // Victory dance
        robot.turnRight(36000);
    }
}
