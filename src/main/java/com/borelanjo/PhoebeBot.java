package com.borelanjo;

import com.borelanjo.behavior.BehaviorClient;
import com.borelanjo.configuration.ColorConfigurator;
import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class PhoebeBot extends AdvancedRobot {
    private final ColorConfigurator colorConfigurator;

    private final BehaviorClient client;

    public PhoebeBot() {
        colorConfigurator = new ColorConfigurator(this);
        client = new BehaviorClient(this);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        colorConfigurator.configure();
        while (true) {
            client.switchBehavior();
            client.getStrategy().run();
        }
    }

    public void onScannedRobot(final ScannedRobotEvent e) {
        client.getStrategy().onScannedRobot(e);
    }

    @Override
    public void onHitRobot(final HitRobotEvent event) {
        client.getStrategy().onHitRobot(event);
    }

    @Override
    public void onWin(final WinEvent event) {
        client.getStrategy().onWin(event);
    }
}
