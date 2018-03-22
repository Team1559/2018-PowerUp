package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.util.PID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WPI_RotateToCube extends Command {

	private final double kP = .079;//0.037 //0.039
	private final double kI = 0.0007;//0.0009
	private final double kD = 0.15;//0.1

	private final double TOLERANCE = 1;//1
	private double angle;
	protected final boolean mecanum;
	private static PID pid;

	public WPI_RotateToCube(boolean mecanum) {
		this.mecanum = mecanum;
		if (pid == null) {
			pid = new PID(kP, kI, kD);
		}
	}

	@Override
	protected void initialize() {
		this.angle = Double.parseDouble(Robot.udp.getAngle()) + Robot.imu.getHeadingRelative();
		pid.reset();
		Robot.driveTrain.shift(mecanum);
		pid.setSetpoint(angle);
	}

	@Override
	protected void execute() {
		// getHeadingRelative() is relative to a zero heading set in autonomousInit(),
		// so it's not super relative.
		Robot.driveTrain.rotate(pid.calculate(Robot.imu.getHeadingRelative()));
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
		return String.format("Rotate(angle=%f, mecanum=%b)", angle, mecanum);
	}
}
