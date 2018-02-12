package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.auto.MotorError;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WPI_TractionTranslate extends Command {
	
	// TODO: Driving forward is a bumpy ride. We periodically send 0 voltage.

	private final double minTime = 0.25;
	// private double time = 0;
	private double TOLERANCE = 300;
	private double dxInTicks;
	private double x;
	private double startTime;

	private double setpoints[];

	public WPI_TractionTranslate(double x) {
		this.x = x;
		this.dxInTicks = x * Constants.WHEEL_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.setpoints = new double[4];
		if (MotorError.cumError == null) {
			MotorError.cumError = new int[4];
		}
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.shift(false);
		System.out.println("Initializing " + this);
		startTime = Timer.getFPGATimestamp();
		Robot.driveTrain.resetQuadEncoders();
		setpoints[DriveTrain.FL] = dxInTicks - MotorError.cumError[DriveTrain.FL];
		setpoints[DriveTrain.FR] = -dxInTicks - MotorError.cumError[DriveTrain.FR];
		setpoints[DriveTrain.RL] = dxInTicks - MotorError.cumError[DriveTrain.RL];
		setpoints[DriveTrain.RR] = -dxInTicks - MotorError.cumError[DriveTrain.RR];
	}

	@Override
	protected void execute() {
		Robot.driveTrain.setSetpoint(setpoints);
	}

	@Override
	protected boolean isFinished() {
		if (Timer.getFPGATimestamp() < startTime + minTime) {
			// timeout
			return false;
		}

		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			SmartDashboard.putNumber("Error for motor " + i + ": ",
					Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0)));
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;

		SmartDashboard.putNumber("Current avg error: ", averageError);
		SmartDashboard.putNumber("P val: ", DriveTrain.kP);
		SmartDashboard.putNumber("Tolerance: ", TOLERANCE);

		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println(this + " has finished");
		for (int i = 0; i < 4; i++) {
			MotorError.cumError[i] = Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
	}

	@Override
	public String toString() {
		return String.format("TractionTranslate(%f)", x);
	}

}