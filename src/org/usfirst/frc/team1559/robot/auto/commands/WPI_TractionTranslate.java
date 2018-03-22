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

	private static final double kP = .09;
	private static final double kI = 0; //was kP/100
	private static final double kD = 23*kP;//22*kP

	private double kTOLERANCE = 0; // 4e6;
	
	private double tolerance;
	
	private double DEFAULT_TOLERANCE = 300; // in ticks, worked with 1500, was 1200
	private double dxInTicks;
	private double x;
	
	private boolean aboveMinRPM = false;
	private double minRPM = 50;
	

	private double setpoints[];

	public WPI_TractionTranslate(double x) {
		this.x = x;
		this.dxInTicks = x * Constants.WHEEL_FUDGE_TRACTION * 4096
				/ (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES_TRACTION);
		this.setpoints = new double[4];
		this.tolerance = DEFAULT_TOLERANCE;
	}

	@Override
	protected void initialize() {
		System.out.println("INITIALIZING " + this + " (t=" + Timer.getFPGATimestamp() + ")");
		Robot.driveTrain.shift(false);
		Robot.driveTrain.setPID(kP, kI, kD);
		Robot.driveTrain.resetQuadEncoders();
		//Left is negative on robot 2, right is negative on robot 1
		setpoints[DriveTrain.FL] = dxInTicks; //NEGATE all of these for robot 2 (currently negated) TODO change back for robot 1
		setpoints[DriveTrain.FR] = -dxInTicks;
		setpoints[DriveTrain.RL] = dxInTicks;
		setpoints[DriveTrain.RR] = -dxInTicks;
	}

	@Override
	protected void execute() {
		Robot.driveTrain.setSetpoint(setpoints);
	}

	@Override
	protected boolean isFinished() {

		// prevents command from finishing before the talon sets its setpoint
		if (this.timeSinceInitialized() <= .25) {
			return false;
		}
		
		//check when we go above minRPM
		if (Robot.driveTrain.getAverageRPM() > minRPM) {
			aboveMinRPM = true;
		}

		//this is for the moving tolerance//
		List<Integer> errors = MathUtils.map((x) -> Math.abs(((WPI_TalonSRX) x).getClosedLoopError(0)), Robot.driveTrain.motors);
		double averageError = MathUtils.average(errors);
		SmartDashboard.putNumber("average error", averageError);
		tolerance += 1.0 / averageError * 1.0 / Robot.driveTrain.getAverageRPM() * kTOLERANCE;
		
		
		//return averageError < tolerance || (Robot.driveTrain.getAverageRPM() < minRPM && aboveMinRPM);
		return Math.abs(Robot.driveTrain.getAverageRPM()) < minRPM && aboveMinRPM;
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
