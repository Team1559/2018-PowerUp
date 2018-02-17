package org.usfirst.frc.team1559.robot.auto.commands;

import java.util.List;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1559.util.MathUtils;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WPI_TractionTranslate extends Command {

	private static final double kP = .059;
	private static final double kI = 0; // .001
	private static final double kD = 0;

	private double TOLERANCE = 300;
	private double dxInTicks;
	private double x;

	private double setpoints[];

	public WPI_TractionTranslate(double x) {
		this.x = x;
		this.dxInTicks = x * Constants.DT_SPROCKET_RATIO * Constants.WHEEL_FUDGE_TRACTION * 4096
				/ (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.setpoints = new double[4];
	}

	@Override
	protected void initialize() {
		System.out.println("INITIALIZING " + this + " (t=" + Timer.getFPGATimestamp() + ")");
		Robot.driveTrain.shift(false);
		Robot.driveTrain.setPID(kP, kI, kD);
		Robot.driveTrain.resetQuadEncoders();
		setpoints[DriveTrain.FL] = -dxInTicks;
		setpoints[DriveTrain.FR] = dxInTicks;
		setpoints[DriveTrain.RL] = -dxInTicks;
		setpoints[DriveTrain.RR] = dxInTicks;
	}

	@Override
	protected void execute() {
		Robot.driveTrain.setSetpoint(setpoints);
	}

	@Override
	protected boolean isFinished() {

		List<Integer> errors = MathUtils.map((x) -> Math.abs(((WPI_TalonSRX) x).getClosedLoopError(0)), Robot.driveTrain.motors);
		double averageError2 = MathUtils.average(errors); // make sure averageError2 == averageError (testing new MathUtil)
		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;
		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println("FINISHING " + this + " (t=" + Timer.getFPGATimestamp() + ")");
	}

	@Override
	public String toString() {
		return String.format("TractionTranslate(%f)", x);
	}
}
