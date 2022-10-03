package com.borelanjo.behavior;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

import java.util.Arrays;
import java.util.List;

public class BehaviorSelector implements Behavior {

    private Behavior behavior;

    private final List<Behavior> behaviors;

    private int currentBehavior = 0;

    private static final float SWITCH_PERCENTAGE = 70f;

    public BehaviorSelector(AdvancedRobot robot) {
        SpinBehavior spinBehavior = new SpinBehavior(robot);
        TrackerBehavior trackerBehavior = new TrackerBehavior(robot);
        TrackFireBehavior trackFireBehavior = new TrackFireBehavior(robot);
        this.behaviors = Arrays.asList(spinBehavior, trackerBehavior, trackFireBehavior);
        this.behavior = behaviors.get(0);
    }


    @Override
    public void run() {
        if (shouldSwitch()) {
            switchBehavior();
        }
        behavior.run();
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        behavior.onScannedRobot(e);
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        behavior.onHitRobot(e);
    }

    @Override
    public void onWin(WinEvent e) {
        behavior.onWin(e);
    }

    public boolean shouldSwitch() {
        double randomValue = Math.random()*100;  //0.0 to 99.9
        return randomValue <= SWITCH_PERCENTAGE;
    }

    private void switchBehavior() {
        currentBehavior = currentBehavior + 1 < behaviors.size() ? currentBehavior + 1 : 0;
        behavior = behaviors.get(currentBehavior);
    }
}
