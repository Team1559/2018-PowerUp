package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WPI_MecanumTranslate extends Command {

	private final double minTime = 0.25;
	private double TOLERANCE;
	private double dxInTicks, dyInTicks;
	private double x, y;
	private double startTime;

	public WPI_MecanumTranslate(double x, double y) {
		this.x = x;
		this.y = y;
		this.dxInTicks = x * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.dyInTicks = y * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		if (x <= 45) {
			TOLERANCE = 300;
		} else {
			TOLERANCE = 992;
		}
		// 0.000817x + 0.0278
		
		DriveTrain.kP = (0.000817 * x) + 0.0278;
		for (WPI_TalonSRX motor : Robot.driveTrain.motors) {
			motor.config_kP(0, DriveTrain.kP, 0);
		}
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
		System.out.println("equation: " + (0.000817 * x + 0.0278));
		System.out
				.println("Average error: " + TOLERANCE + ", P: " + DriveTrain.kP + " , TOLERANCE: " + TOLERANCE);
		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println(this + " IS FINISHED");
	}

	@Override
	public String toString() {
		return String.format("MecanumTranslate(x=%f, y=%f)", x, y);
	}

}
