package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WPI_MecanumTranslate extends Command {

	private final double minTime = 0.25;
	private double time = 0;
	private double TOLERANCE;
	private double dxInTicks, dyInTicks;
	private double x, y;
	private double startTime;

	public WPI_MecanumTranslate(double x, double y) {
		this.x = x;
		this.y = y;
		// radius 3 , fudge 0.85, 3481.6
		this.dxInTicks = x * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.dyInTicks = y * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		if (x <= 45 || y <= 45) {
			TOLERANCE = 300;
		} else {
			TOLERANCE = 992;
		}
		// 0.000817x + 0.0278
		if (x != 0)
			DriveTrain.kP = (0.000817 * Math.abs(x)) + 0.0278;
		else
			DriveTrain.kP = (0.000817 * Math.abs(y)) + 0.0278;
		byte k = 0;
		for (WPI_TalonSRX motor : Robot.driveTrain.motors) {
			k++;
			motor.config_kP(0, DriveTrain.kP, 0);
		}
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.resetQuadEncoders();
		Robot.driveTrain.shift(true);
		System.out.println("Initializing " + this);
		startTime = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		Robot.driveTrain.translateAbsolute(dxInTicks, dyInTicks);
	}

	@Override
	protected boolean isFinished() {
		SmartDashboard.putString("Command (" + index + ") time: ", String.valueOf(time));
		if (Timer.getFPGATimestamp() < startTime + minTime) {
			return false;
		}
		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			System.out.println("Error for motor " + i + ": " + Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0)));
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;
		SmartDashboard.putNumber("Current avg error: ", averageError);
		SmartDashboard.putNumber("P val: ", DriveTrain.kP);
		SmartDashboard.putNumber("Tolerance: ", TOLERANCE);
		SmartDashboard.putNumber("Current command index: ", index);
		if (averageError < TOLERANCE == true) {
			index++;
			SmartDashboard.putString("Command (" + index + ") status: ", "All good!");
		}
		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println(this + " has finished");
	}

	@Override
	public String toString() {
		return String.format("MecanumTranslate (" + index + ") (%f, %f)", x, y);
	}

}
