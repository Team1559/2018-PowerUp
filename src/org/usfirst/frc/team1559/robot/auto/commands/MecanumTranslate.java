package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.auto.AutoCommand;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

/**
 * Moves the command forward (or backward if given a negative distance)
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class MecanumTranslate extends AutoCommand {

	private double x, y;
	
	public MecanumTranslate(double x, double y, AutoStrategy parent) {
		this.parent = parent;
		this.x = x;
		this.y = y;
	}

	@Override
	public void init() {
		type = AutoCommand.TYPE_MOVE;
		isInitialized = true;
		Robot.driveTrain.resetEncoders();
	}

	@Override
	public void iterate() {
		double xInTicks = x * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		double yInTicks = y * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		Robot.driveTrain.translate(xInTicks, yInTicks);
	}
}
