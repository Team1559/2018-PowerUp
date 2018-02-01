package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WPI_MecanumTranslate extends Command {

	private final double minTime = 0.25;
	private final double TOLERANCE = 666;
	private double dxInTicks, dyInTicks;
	private double x, y;
	private double startTime;

	public WPI_MecanumTranslate(double x, double y) {
		this.x = x;
		this.y = y;
		this.dxInTicks = x * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.dyInTicks = y * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.resetQuadEncoders();
		Robot.driveTrain.shift(true);
		System.out.println("INIT " + this);
		startTime = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		Robot.driveTrain.translateAbsolute(dxInTicks, dyInTicks);
	}

	@Override
	protected boolean isFinished() {
		if (Timer.getFPGATimestamp() < startTime + minTime) {
			return false;
		}
		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;
		System.out.println(averageError);
		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println(this + " IS FINISHED");
	}

	@Override
	public String toString() {
		return String.format("MecanumTranslate(%f, %f)", x, y);
	}

}
