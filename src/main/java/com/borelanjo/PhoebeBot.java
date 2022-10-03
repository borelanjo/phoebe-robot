package com.borelanjo;

import com.borelanjo.behavior.BehaviorSelector;
import com.borelanjo.configuration.ColorConfigurator;
import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

public class PhoebeBot extends AdvancedRobot {
    private final ColorConfigurator colorConfigurator;

    private final BehaviorSelector behaviorSelector;

    public PhoebeBot() {
        colorConfigurator = new ColorConfigurator(this);
        behaviorSelector = new BehaviorSelector(this);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        colorConfigurator.configure();
        while (true) {
            behaviorSelector.run();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        behaviorSelector.onScannedRobot(e);
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        behaviorSelector.onHitRobot(event);
    }
}
