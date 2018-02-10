package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.util.PID;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_RotateToTarget extends Command {

	//TODO: Weird oscillating error with jerky motions on second run
	//TODO: PID loop tuning (consider PI)
	
	private final double kP = .02;//0.053;
	private final double kI = 0.001;
	private final double kD = 0;//.2;

	private final double TOLERANCE = 1;
	private double angle;
	private final boolean mecanum;
	private static PID pid;

	public WPI_RotateToTarget(double angle, boolean mecanum) {
		this.angle = angle;
		this.mecanum = mecanum;
		if (pid == null) {
			pid = new PID(kP, kI, kD);
		}
	}

	@Override
	protected void initialize() {
		pid.reset();
		Robot.driveTrain.shift(mecanum);
		pid.setSetpoint(angle);
	}

	@Override
	protected void execute() {
		// getHeadingRelative() is relative to a zero heading set in autonomousInit(), so it's not super relative.
		Robot.driveTrain.rotate(-1 * pid.calculate(Robot.imu.getHeadingRelative()));
	}

	@Override
	protected boolean isFinished() {
		return pid.onTarget(TOLERANCE);
	}

	@Override
	protected void end() {
		System.out.println(this + " IS FINISHED");
	}

	@Override
	public String toString() {
		return String.format("Rotate(angle=%f, mecanum=%b)", angle, mecanum);
	}
}