package com.borelanjo.configuration;

import robocode.Robot;

import java.awt.*;

public class ColorConfigurator {

    private final Robot robot;

    public ColorConfigurator(Robot robot) {
        this.robot = robot;
    }

    public void configure() {
        configureColor();
        robot.setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
    }

    private void configureColor() {
        robot.setBodyColor(Color.WHITE);
        robot.setGunColor(Color.BLACK);
        robot.setRadarColor(Color.DARK_GRAY);
        robot.setBulletColor(Color.RED);
        robot.setScanColor(Color.WHITE);
    }
}
