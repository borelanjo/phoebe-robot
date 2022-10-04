package com.borelanjo.behavior.strategy;

import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public interface Strategy {

    void run();

    void onScannedRobot(ScannedRobotEvent e);

    void onHitRobot(HitRobotEvent e);

    void onWin(WinEvent e);

}
