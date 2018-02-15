package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WPI_TractionTranslate extends Command {

	private static final double kP = .089;
	private static final double kI = 0.001;
	private static final double kD = 0;

	private final double minTime = 0.25;
	private double TOLERANCE = 300;
	private double dxInTicks;
	private double x;
	private double startTime;

	public static int[] incremental;
	private double setpoints[];

	public WPI_TractionTranslate(double x) {
		this.x = x;
		this.dxInTicks = x * Constants.DT_SPROCKET_RATIO * Constants.WHEEL_FUDGE_TRACTION * 4096
				/ (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.setpoints = new double[4];
    if (incremental == null) {
			incremental = new int[4];
		}
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.shift(false);
		Robot.driveTrain.setPID(kP, kI, kD);
		System.out.println("Initializing " + this);
		startTime = Timer.getFPGATimestamp();
		Robot.driveTrain.resetQuadEncoders();
		setpoints[DriveTrain.FL] = drxInTicks;
		setpoints[DriveTrain.FR] = -dxInTicks;
		setpoints[DriveTrain.RL] = dxInTicks;
		setpoints[DriveTrain.RR] = -dxInTicks;
	}

	@Override
	protected void execute() {
		Robot.driveTrain.setpoint(setpoints);
	}

	@Override
	protected boolean isFinished() {
		if (Timer.getFPGATimestamp() < startTime + minTime) {
			return false;
		}
		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;
		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println(this + " has finished");
		for (int i = 0; i < 4; i++) {
			incremental[i] = Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
	}

	@Override
	public String toString() {
		return String.format("TractionTranslate(%f inches)", x);
	}

}
