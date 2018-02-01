package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_RotateRel extends Command {

	private final double kP = .02;

	private final double TOLERANCE = 1;
	private double angle, startAngle;
	private final boolean mecanum;

	public WPI_RotateRel(double angle, boolean mecanum) {
		this.angle = angle;
		this.mecanum = mecanum;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.shift(mecanum);
		startAngle = Robot.imu.getHeading();
	}

	@Override
	protected void execute() {
		Robot.driveTrain.rotate(kP * (Robot.imu.getHeading() - (startAngle + angle)));
	}

	@Override
	protected boolean isFinished() {
		System.out.println("error" + (Robot.imu.getHeading() - (startAngle + angle)));
		return Math.abs(Robot.imu.getHeading() - (startAngle + angle)) < TOLERANCE;
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
