package org.usfirst.frc.team1559.robot.auto.commands;

import java.util.List;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1559.util.MathUtils;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WPI_TractionTranslate extends Command {

	private static final double kP = .09;//.61;
	private static final double kI = 0;
	private static final double kD = 25*kP;

	private double TOLERANCE = 1000;
	private double dxInTicks;
	private double x;

	private double setpoints[];

	public WPI_TractionTranslate(double x) {
		this.x = x;
		this.dxInTicks = x * Constants.WHEEL_FUDGE_TRACTION * 4096
				/ (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES_TRACTION);
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

		if (this.timeSinceInitialized() <= .25) {
			return false;
		}
		
		List<Integer> errors = MathUtils.map((x) -> Math.abs(((WPI_TalonSRX) x).getClosedLoopError(0)), Robot.driveTrain.motors);
		double medianError2 = MathUtils.median(errors); // make sure averageError2 == averageError (testing new MathUtil)
		double medianError = 0;
		SmartDashboard.putNumber("Median Error", medianError2);
		for (int i = 0; i < 4; i++) {
			medianError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		medianError /= 4;
		return medianError2 < TOLERANCE;
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
