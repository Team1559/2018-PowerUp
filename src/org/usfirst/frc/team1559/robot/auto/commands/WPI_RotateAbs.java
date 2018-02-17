package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.util.PID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command that rotates the robot to a given angle (absolute)
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class WPI_RotateAbs extends Command {

	// TODO: Weird oscillating error with jerky motions on second run

	private final double kP = .04;
	private final double kI = 0.0009;
	private final double kD = 0.1;
	private final double TOLERANCE = 1;
	private final boolean isMecanum;

	private double angle;

	private static PID pid;
	
	public WPI_RotateAbs(double angle, boolean mecanum) {
		this.angle = angle;
		this.isMecanum = mecanum;
		if (pid == null) {
			pid = new PID(kP, kI, kD);
		}
	}

	@Override
	protected void initialize() {
		pid.reset();
		Robot.driveTrain.shift(isMecanum);
		pid.setSetpoint(angle);
	}

	@Override
	protected void execute() {
		// getHeadingRelative() is relative to a zero heading set in autonomousInit(),
		// so it's not super relative.
		Robot.driveTrain.rotate(-1 * pid.calculate(Robot.imu.getHeadingRelative()));
		SmartDashboard.putNumber("Heading", Robot.imu.getHeadingRelative());
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
		return String.format("Rotate(angle=%f, mecanum=%b)", angle, isMecanum);
	}
}
