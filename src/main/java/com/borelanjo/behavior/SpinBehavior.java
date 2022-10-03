package com.borelanjo.behavior;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

import java.awt.*;

public class SpinBehavior implements Behavior {

    private final AdvancedRobot robot;

    public SpinBehavior(AdvancedRobot robot) {
        this.robot = robot;
    }

    /**
     * SpinBot's run method - Circle
     */
    @Override
    public void run() {
        robot.setAdjustGunForRobotTurn(false); // Keep the gun still when we turn

        // Set colors
        robot.setGunColor(Color.blue);
        // Tell the game that when we take move,
        // we'll also want to turn right... a lot.
        robot.setTurnRight(10000);
        // Limit our speed to 5
        robot.setMaxVelocity(5);
        // Start moving (and turning)
        robot.ahead(10000);
        // Repeat.

    }

    /**
     * onScannedRobot: Fire hard!
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        robot.fire(3);
    }

    /**
     * onHitRobot:  If it's our fault, we'll stop turning and moving,
     * so we need to turn again to keep spinning.
     */
    @Override
    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() > -10 && e.getBearing() < 10) {
            robot.fire(3);
        }
        if (e.isMyFault()) {
            robot.turnRight(10);
        }
    }

    @Override
    public void onWin(WinEvent e) {
    }
}
