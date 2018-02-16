package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WPI_TractionTranslate extends Command {

	private static final double kP = .089;// .089 EVERY .11 you increment it it goes 2 more inches
	private static final double kI = 0.00; // .001
	private static final double kD = 0;

	private final double minTime = 3;
	// private double time = 0;
	private double TOLERANCE = 300;
	private double dxInTicks;
	private double x;
	private double startTime;

	private double setpoints[];

	public WPI_TractionTranslate(double x) {
		this.x = x;
		this.dxInTicks = x * Constants.DT_SPROCKET_RATIO * Constants.WHEEL_FUDGE_TRACTION * 4096
				/ (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.setpoints = new double[4];

		/*
		 * if (x <= 45 || y <= 45) { TOLERANCE = 300; } else { TOLERANCE = 992; } //
		 * 0.000817x + 0.0278 /*if (x != 0) DriveTrain.kP = (0.000817 * Math.abs(x)) +
		 * 0.0278; else DriveTrain.kP = (0.000817 * Math.abs(y)) + 0.0278; for
		 * (WPI_TalonSRX motor : Robot.driveTrain.motors) { motor.config_kP(0,
		 * DriveTrain.kP, 0); }
		 */

	}

	@Override
	protected void initialize() {
		Robot.driveTrain.shift(false);
		Robot.driveTrain.setPID(kP, kI, kD);
		System.out.println("Initializing " + this);
		startTime = Timer.getFPGATimestamp();
		Robot.driveTrain.resetQuadEncoders();
		setpoints[DriveTrain.FL] = dxInTicks;
		setpoints[DriveTrain.FR] = -dxInTicks;
		setpoints[DriveTrain.RL] = dxInTicks;
		setpoints[DriveTrain.RR] = -dxInTicks;
	}

	@Override
	protected void execute() {
		// double[] s = Robot.driveTrain.rotateVector(dxInTicks, dyInTicks,
		// Robot.imu.getVector()[0]);
		// setpoints[DriveTrain.FL] = s[0] + s[1];
		// setpoints[DriveTrain.FR] = -s[0] + s[1];
		// setpoints[DriveTrain.RL] = s[0] - s[1];
		// setpoints[DriveTrain.RR] = -s[0] - s[1];
		Robot.driveTrain.setSetpoint(setpoints);
	}

	@Override
	protected boolean isFinished() {
		/*
		 * System.out.println("now: " + Timer.getFPGATimestamp());
		 * System.out.println("start: " + startTime + "\n"); if
		 * (Timer.getFPGATimestamp() < startTime + minTime) { return false; }
		 */
		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;
		System.out.println("finished: " + (averageError < TOLERANCE) + " (" + averageError + " < " + TOLERANCE + ")");
		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println(this + " has finished");
	}

	@Override
	public String toString() {
		return String.format("TractionTranslate(%f)", x);
	}

}
