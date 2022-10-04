package com.borelanjo.behavior.strategy;

public class StrategyContext {

    private Strategy strategy;

    public StrategyContext(final Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
