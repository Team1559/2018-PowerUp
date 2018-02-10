package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WPI_MecanumTranslate extends Command {

	public static int cumError[] = new int[4];
	
	private final double minTime = 0.25;
	// private double time = 0;
	private double TOLERANCE = 300;
	private double dxInTicks, dyInTicks;
	private double x, y;
	private double startTime;

	private double angle, angleInTicks;
	private double setpoints[];
	
	public WPI_MecanumTranslate(double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.dyInTicks = y * Constants.WHEEL_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.dxInTicks = x * Constants.WHEEL_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.angleInTicks = angle * 4096 * 0.019;
		this.setpoints = new double[4];
		
		/*
		if (x <= 45 || y <= 45) {
			TOLERANCE = 300;
		} else {
			TOLERANCE = 992;
		}
		// 0.000817x + 0.0278
		/*if (x != 0)
			DriveTrain.kP = (0.000817 * Math.abs(x)) + 0.0278;
		else
			DriveTrain.kP = (0.000817 * Math.abs(y)) + 0.0278;
		for (WPI_TalonSRX motor : Robot.driveTrain.motors) {
			motor.config_kP(0, DriveTrain.kP, 0);
		}
		*/

	}

	@Override
	protected void initialize() {
		Robot.driveTrain.shift(true);
		System.out.println("Initializing " + this);
		startTime = Timer.getFPGATimestamp();
		Robot.driveTrain.resetQuadEncoders();
		setpoints[DriveTrain.FL] = dxInTicks + dyInTicks - angleInTicks - cumError[DriveTrain.FL];
		setpoints[DriveTrain.FR] = -dxInTicks + dyInTicks - angleInTicks - cumError[DriveTrain.FR];
		setpoints[DriveTrain.RL] = dxInTicks - dyInTicks - angleInTicks - cumError[DriveTrain.RL];
		setpoints[DriveTrain.RR] = -dxInTicks - dyInTicks - angleInTicks - cumError[DriveTrain.RR];
	}

	@Override
	protected void execute() {
		Robot.driveTrain.setSetpoint(setpoints);
	}

	@Override
	protected boolean isFinished() {
		if (Timer.getFPGATimestamp() < startTime + minTime) {
			return false;
		}
		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			SmartDashboard.putNumber("Error for motor " + i + ": ", Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0)));
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;
		SmartDashboard.putNumber("Current avg error: ", averageError);
		SmartDashboard.putNumber("P val: ", DriveTrain.kP);
		SmartDashboard.putNumber("Tolerance: ", TOLERANCE);
		if (averageError < TOLERANCE == true) {
			SmartDashboard.putString("Command status: ", "All good!");
		}
		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println(this + " has finished");
		for (int i = 0; i < 4; i++) {
			cumError[i] = Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
	}

	@Override
	public String toString() {
		return String.format("MecanumTranslate (%f, %f, %f degrees)", x, y, angle);
	}

}
