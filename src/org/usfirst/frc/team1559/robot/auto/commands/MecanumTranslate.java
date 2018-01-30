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

	private double dxInTicks, dyInTicks;
	private final double CLOSED_LOOP_ERROR_TOLERANCE = 666;

	public MecanumTranslate(double x, double y, AutoStrategy parent) {
		this.parent = parent;
		this.dxInTicks = x * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.dyInTicks = y * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
	}

	@Override
	public void initialize() {
		type = AutoCommand.TYPE_MOVE;
		Robot.driveTrain.shift(true);
	}

	@Override
	public void iterate() {
		Robot.driveTrain.translateAbsolute(dxInTicks, dyInTicks);
		// check if done after moving (set isDone to true when done)
		// *****************************
		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;
		System.out.println("average error " + averageError);
		isDone = averageError < CLOSED_LOOP_ERROR_TOLERANCE;
		// *****************************
	}
}
