package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.util.PID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WPI_MoveToCube extends Command {

	// TODO: Weird oscillating error with jerky motions on second run
	// TODO: PID loop tuning (consider PI)

	private final double kP = .04;
	private final double kI = 0.0009;
	private final double kD = 0.1;

	private final double TOLERANCE = 1;
	private double angle;
	private final boolean mecanum;
	private static PID pid;

	public WPI_MoveToCube(boolean mecanum) {
		this.mecanum = mecanum;
		if (pid == null) {
			pid = new PID(kP, kI, kD);
		}
	}

	@Override
	protected void initialize() {
		System.out.println("INITIALIZING " + this);
		this.angle = Robot.imu.getHeadingRelative() + Robot.udp.getAngle();
		pid.reset();
		Robot.driveTrain.shift(mecanum);
		pid.setSetpoint(angle);
	}

	@Override
	protected void execute() {
		System.out.println(Robot.imu.getHeadingRelative() + ", " + this.angle);
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
		return String.format("Rotate(mecanum=%b)", mecanum);
	}
}
