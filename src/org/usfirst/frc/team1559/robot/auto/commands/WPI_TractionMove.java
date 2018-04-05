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

public class WPI_TractionMove extends Command {

	//TODO tweak these values, possibly adjust by a factor of 1023 or 1024
	private static PID pid;
	private static final double kP = .09/1023;//.05
	private static final double kI = 0; 
	private static final double kD = 3*kP;//0.188;
	
	private double kTOLERANCE = 0; // 4e6;
	private double tolerance;
	private double DEFAULT_TOLERANCE = 300; // in ticks, worked with 1500, was 1200
	private double dxInTicks;
	private double x;
	
	private boolean aboveMinRPM = false;
	private double minRPM = 100;
	
	private double setpoint;
	private double setpercent;
	
	private double targetAngle;
	private double spin_kP = 0.037;
	private double spin_kI = 0.0007;
	private double spin_kD = 0.15;
	private static PID spinPID;
	
	
	public WPI_TractionMove(double x, double targetAngle) {
		this.x = x;
		this.dxInTicks = x * Constants.WHEEL_FUDGE_TRACTION * 4096
				/ (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES_TRACTION);
		this.pid = new PID();
		this.tolerance = DEFAULT_TOLERANCE;
		this.targetAngle = targetAngle;
		
		pid = new PID(kP,kI,kD);
		spinPID = new PID(spin_kP, spin_kI, spin_kD);
	}

	@Override
	protected void initialize() {
		System.out.println("INITIALIZING " + this + " (t=" + Timer.getFPGATimestamp() + ")");
		
		Robot.driveTrain.shift(false);
		Robot.driveTrain.resetQuadEncoders();
		
		setpoint = dxInTicks;
		
		pid.reset();
		pid.setSetpoint(setpoint);
		
		spinPID.reset();
		spinPID.setSetpoint(this.targetAngle);
	}
	
	protected void execute() {
		
		//SmartDashboard.putNumber("Motor 0 RPM", Robot.driveTrain.getMotors()[0].getSensorCollection().getQuadratureVelocity());
		
		SmartDashboard.putNumber("average position", Robot.driveTrain.getAveragePosition());
		setpercent = pid.calculate(Robot.driveTrain.getAveragePosition());
		System.out.println(Robot.driveTrain.getAveragePosition()+","+setpoint);
		
		//adjust for the error angle
		//double errorAngle = targetAngle - Robot.imu.getHeadingRelative();
		double R = spinPID.calculate(Robot.imu.getHeadingRelative());
		
		//TODO this is probably positive on robot 1
		SmartDashboard.putNumber("R", R);
		Robot.driveTrain.drive(-setpercent,0,-R);
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
