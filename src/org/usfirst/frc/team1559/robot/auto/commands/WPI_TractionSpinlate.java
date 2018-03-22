package org.usfirst.frc.team1559.robot.auto.commands;

import java.util.List;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1559.util.MathUtils;
import org.usfirst.frc.team1559.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WPI_TractionSpinlate extends Command {

	//TODO tweak these values, possibly adjust by a factor of 1023 or 1024
	private static PID[] pids;
	private static final double kP = .05/1023; //was 0.09
	private static final double kI = 0; //was kP/100
	private static final double kD = 23*kP/1023;//22*kP

	private double kTOLERANCE = 0; // 4e6;
	private double tolerance;
	private double DEFAULT_TOLERANCE = 300; // in ticks, worked with 1500, was 1200
	private double dxInTicks;
	private double x;
	
	private boolean aboveMinRPM = false;
	private double minRPM = 100;
	
	private double setpoints[];
	private double setpercents[];
	
	private double targetAngle;
	private double spin_kP = 0.037;
	private double spin_kI = 0.0007;
	private double spin_kD = 0.15;
	private static PID spinPID;
	private double exitAngle;
	
	
	public WPI_TractionSpinlate(double x, double targetAngle) {
		this.x = x;
		this.dxInTicks = x * Constants.WHEEL_FUDGE_TRACTION * 4096
				/ (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES_TRACTION);
		this.setpoints = new double[4];
		this.setpercents = new double[4];
		this.pids = new PID[4];
		this.tolerance = DEFAULT_TOLERANCE;
		//this.exitAngle = exitAngle;
		this.targetAngle = targetAngle;
		
		for(int i = 0; i <= 3; i++) {
			pids[i] = new PID(kP,kI,kD);
		}
		spinPID = new PID(spin_kP, spin_kI, spin_kD);
	}

	@Override
	protected void initialize() {
		System.out.println("INITIALIZING " + this + " (t=" + Timer.getFPGATimestamp() + ")");
		//relative
		//this.targetAngle = Robot.imu.getHeadingRelative() + this.exitAngle;
		//absolute
		//this.targetAngle = this.exitAngle;
		Robot.driveTrain.shift(false);
		Robot.driveTrain.resetQuadEncoders();
		//Left is negative on robot 2, right is negative on robot 1
		setpoints[DriveTrain.FL] = dxInTicks; //NEGATE all of these for robot 2 (currently negated) TODO change back for robot 1
		setpoints[DriveTrain.FR] = -dxInTicks;
		setpoints[DriveTrain.RL] = dxInTicks;
		setpoints[DriveTrain.RR] = -dxInTicks;
		
		for(int i = 0; i <= 3; i++) {
			pids[i].reset();
			pids[i].setSetpoint(setpoints[i]);
		}
		spinPID.reset();
		spinPID.setSetpoint(this.targetAngle);
	}
	
	protected void execute() {
		for(int i = 0; i <= 3; i++) {
			double x = pids[i].calculate(Robot.driveTrain.getMotors()[i].getSensorCollection().getQuadraturePosition());
			setpercents[i] = x;
		}
		
		//adjust for the error angle
		//double errorAngle = targetAngle - Robot.imu.getHeadingRelative();
		double R = spinPID.calculate(Robot.imu.getHeadingRelative());
		
		//TODO check these signs for robot 1
		setpercents[DriveTrain.FL] -= R/(2*Math.abs(setpercents[DriveTrain.FL])+1);
		setpercents[DriveTrain.FR] -= R/(2*Math.abs(setpercents[DriveTrain.FR])+1);
		setpercents[DriveTrain.RL] -= R/(2*Math.abs(setpercents[DriveTrain.RL])+1);
		setpercents[DriveTrain.RR] -= R/(2*Math.abs(setpercents[DriveTrain.RR])+1);
		
		//TODO take this out
//		SmartDashboard.putNumber("R", R);
//		SmartDashboard.putNumber("FL percent", setpercents[DriveTrain.FL]);
//		SmartDashboard.putNumber("FR percent", setpercents[DriveTrain.FR]);
//		SmartDashboard.putNumber("RL percent", setpercents[DriveTrain.RL]);
//		SmartDashboard.putNumber("RR percent", setpercents[DriveTrain.RR]);
//		SmartDashboard.putNumber("Error FL", setpoints[0]-Robot.driveTrain.getMotors()[0].getSensorCollection().getQuadraturePosition());
		
		Robot.driveTrain.getMotors()[DriveTrain.FL].set(ControlMode.PercentOutput, setpercents[DriveTrain.FL]);
		Robot.driveTrain.getMotors()[DriveTrain.FR].set(ControlMode.PercentOutput, setpercents[DriveTrain.FR]);
		Robot.driveTrain.getMotors()[DriveTrain.RL].set(ControlMode.PercentOutput, setpercents[DriveTrain.RL]);
		Robot.driveTrain.getMotors()[DriveTrain.RR].set(ControlMode.PercentOutput, setpercents[DriveTrain.RR]);
	}

	@Override
	protected boolean isFinished() {
		//return false;
		
		
		// prevents command from finishing before the talon sets its setpoint
		if (this.timeSinceInitialized() <= .25) {
			return false;
		}
		
		//check when we go above minRPM
		if (Robot.driveTrain.getAbsoluteAverageRPM() > minRPM) {
			aboveMinRPM = true;
		}
		
		//this is for the moving tolerance//
		List<Integer> errors = MathUtils.map((x) -> Math.abs(((WPI_TalonSRX) x).getClosedLoopError(0)), Robot.driveTrain.motors);
		double averageError = MathUtils.average(errors);
		tolerance += 1.0 / averageError * 1.0 / Robot.driveTrain.getAverageRPM() * kTOLERANCE;
				
		//return averageError < tolerance || (Robot.driveTrain.getAverageRPM() < minRPM && aboveMinRPM);
		return Math.abs(Robot.driveTrain.getAbsoluteAverageRPM()) < minRPM && aboveMinRPM;
		
		
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
