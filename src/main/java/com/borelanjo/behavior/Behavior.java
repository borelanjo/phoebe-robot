package com.borelanjo.behavior;

import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public interface Behavior {

    void run();

    void onScannedRobot(ScannedRobotEvent e);

    void onHitRobot(HitRobotEvent e);

    void onWin(WinEvent e);

}
