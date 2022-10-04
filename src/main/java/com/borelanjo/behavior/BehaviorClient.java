package com.borelanjo.behavior;

import com.borelanjo.behavior.strategy.*;
import robocode.AdvancedRobot;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.shuffle;

public class BehaviorClient {

    private final List<Strategy> strategies;

    private final StrategyContext context;

    private int currentBehavior = 0;

    private static final float SWITCH_PERCENTAGE = 70f;

    public BehaviorClient(final AdvancedRobot robot) {
        SpinStrategy spinStrategy = new SpinStrategy(robot);
        TrackerStrategy trackerBehavior = new TrackerStrategy(robot);
        TrackFireStrategy trackFireStrategy = new TrackFireStrategy(robot);

        this.strategies = Arrays.asList(spinStrategy, trackerBehavior, trackFireStrategy);
        shuffle(this.strategies);

        this.context = new StrategyContext(this.strategies.get(currentBehavior));
    }

    public Strategy getStrategy() {
        return context.getStrategy();
    }

    public void drawStrategy() {
        if (shouldSwitch()) {
            currentBehavior = currentBehavior + 1 < strategies.size() ? currentBehavior + 1 : 0;
            this.context.setStrategy(strategies.get(currentBehavior));
            shuffle(this.strategies);
        }

    }

    private boolean shouldSwitch() {
        double randomValue = Math.random() * 100;  //0.0 to 99.9
        return randomValue <= SWITCH_PERCENTAGE;
    }

}
