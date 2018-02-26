package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

public class WPI_RotateToCube extends WPI_RotateAbs {

	public WPI_RotateToCube(boolean mecanum) {
		super(Robot.visionData.angle + Robot.imu.getHeadingRelative(), mecanum);
	}

	@Override
	public String toString() {
		return String.format("RotateToCube(mecanum=%b)", mecanum);
	}
}
