package org.usfirst.frc.team1559.robot.autoWORK.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.autoWORK.AutoCommand;

public class MecanumMove extends AutoCommand {

	private double dxInTicks;
	private double dyInTicks;
	private double averageClosedLoopError;
	private final double CLOSED_LOOP_ERROR_TOLERANCE = 666;

	/**
	 * Creates a new mecanum movement commmand, which traverses the given
	 * distance(s)
	 * 
	 * @param x
	 *            Distance to go forward in inches
	 * @param y
	 *            Distance to go sideways in inches
	 */
	public MecanumMove(double x, double y) {
		super();
		dxInTicks = x * Constants.WHEEL_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		dyInTicks = y * Constants.WHEEL_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
	}

	@Override
	public void init() {
		Robot.driveTrain.shift(true);
	}

	@Override
	public void go() {
		Robot.driveTrain.translateAbsolute(dxInTicks, dyInTicks);

		// check if done after moving (set isDone to true when done)
		// *****************************
		for (int i = 0; i < 4; i++) {
			averageClosedLoopError += Math.abs(Robot.driveTrain.getMotors()[i].getClosedLoopError(0));
		}

		averageClosedLoopError /= 4;
		System.out.println("[MecanumMove] ~Error: " + averageClosedLoopError);

		isFinished = averageClosedLoopError < CLOSED_LOOP_ERROR_TOLERANCE;

		if (isFinished) {
			System.out.println("One of the MecanumMove commands has finished");
		}
		// *****************************
	}

}
