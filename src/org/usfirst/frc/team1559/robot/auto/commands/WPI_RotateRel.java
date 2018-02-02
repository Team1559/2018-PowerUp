package org.usfirst.frc.team1559.robot.auto.commands;

import java.nio.channels.ShutdownChannelGroupException;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.util.PID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WPI_RotateRel extends Command {

	//TODO: Weird oscillating error with jerky motions
	//TODO: PID loop tuning (consider PI)
	
	private final double kP = 0.01;
	private final double kI = 0;
	private final double kD = 0;

	private final double TOLERANCE = 1;
	private double angle, startAngle;
	private double error;
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
	}

	@Override
	protected void execute() {
		pid.setSetpoint(startAngle + angle);
		double out = pid.calculate(Robot.imu.getHeading());
		Robot.driveTrain.rotate(-out);
	}

	@Override
	protected boolean isFinished() {
		SmartDashboard.putNumber("Error", pid.getError());
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
