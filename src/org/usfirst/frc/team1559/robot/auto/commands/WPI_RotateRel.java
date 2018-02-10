package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.util.PID;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_RotateRel extends Command {

	//TODO: Weird oscillating error with jerky motions
	//TODO: PID loop tuning (consider PI)
	
	private final double kP = 0.01;
	private final double kI = 0;
	private final double kD = 0;

	private final double TOLERANCE = 1;
	private double angle, startAngle;
	private final boolean mecanum;
	private PID pid;

	public WPI_RotateRel(double angle, boolean mecanum) {
		this.angle = angle;
		this.mecanum = mecanum;
		pid = new PID(kP, kI, kD);
		pid.setContinuous(true);
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.shift(mecanum);
		startAngle = Robot.imu.getHeading();
		pid.setSetpoint(startAngle + angle);
	}

	@Override
	protected void execute() {
		Robot.driveTrain.rotate(-1 * pid.calculate(Robot.imu.getHeading()));
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
