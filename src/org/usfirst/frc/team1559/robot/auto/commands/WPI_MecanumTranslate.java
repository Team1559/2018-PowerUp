package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_MecanumTranslate extends Command {

	private final double TOLERANCE = 666;
	private double dxInTicks, dyInTicks;
	private int startTime = 250, currentTime;
	
	public WPI_MecanumTranslate(double x, double y) {
		this.dxInTicks = x * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.dyInTicks = y * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
	}
	@Override
	protected void initialize() {
		Robot.driveTrain.resetQuadEncoders();
		Robot.driveTrain.shift(true);
		System.out.println("INIT");
	}

	@Override
	protected void execute() {
		Robot.driveTrain.translateAbsolute(dxInTicks, dyInTicks);
		System.out.println("EXECUTE");
	}

	@Override
	protected boolean isFinished() {
		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;
		System.out.println("average error " + averageError);
		currentTime++;
		return averageError < TOLERANCE && currentTime > startTime;
	}

	@Override
	protected void end() {
		
	}

}
