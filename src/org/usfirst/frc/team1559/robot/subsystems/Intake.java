package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.util.MathUtils;
import org.usfirst.frc.team1559.util.PID;
import org.usfirst.frc.team1559.util.PIDFGains;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

	private Solenoid solenoid;
	private Spark sparkLeft;
	private Spark sparkRight;
	private WPI_TalonSRX shoulder;
	private double shoulderSetpoint;
	
	private PID pid;

	private final PIDFGains shoulderPidf;

	private static final int TIMEOUT = 0;

	private double angle = 0;
	private double ticksAt0 = 675;
	private double ticksAt90 = 316;

	public Intake() {
		solenoid = new Solenoid(Wiring.NTK_SOLENOID);
		sparkLeft = new Spark(Wiring.NTK_SPARK_LEFT);
		sparkRight = new Spark(Wiring.NTK_SPARK_RIGHT);

		shoulder = new WPI_TalonSRX(Wiring.NTK_TALON_ROTATE);
		
		pid = new PID(1.0/300.0,0,0);
		pid.reset();
		
		shoulderPidf = new PIDFGains(4.11, 0, 0, 0.4);

		shoulder.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);

		shoulder.configForwardSoftLimitEnable(false, TIMEOUT);
		shoulder.configReverseSoftLimitEnable(false, TIMEOUT);

		//forward is down, reverse is up
		shoulder.configNominalOutputForward(+0, TIMEOUT); 
		shoulder.configNominalOutputReverse(-0, TIMEOUT); 
		shoulder.configPeakOutputForward(+1, TIMEOUT);
		shoulder.configPeakOutputReverse(-1, TIMEOUT);
		
		shoulder.enableCurrentLimit(false);
		
		shoulder.config_kP(0, shoulderPidf.kP, TIMEOUT);
		shoulder.config_kI(0, shoulderPidf.kI, TIMEOUT);
		shoulder.config_kD(0, shoulderPidf.kD, TIMEOUT);
		shoulder.config_kF(0, shoulderPidf.kF, TIMEOUT);
		
		shoulder.setSensorPhase(false);
		shoulder.setNeutralMode(NeutralMode.Brake);
		
		shoulder.set(ControlMode.Velocity, 0);

		if (!Robot.robotOne) {
			ticksAt0 = 675;
			ticksAt90 = 316;
		}
	}

	public void open() {
		solenoid.set(true); // true
	}

	public void close() {
		solenoid.set(false); // false
	}

	public void toggle() {
		solenoid.set(!solenoid.get());
	}

	public void out(double speed) {
		//sparkLeft.set(-speed);
		//sparkRight.set(speed);
	}

	public void out() {
		//sparkLeft.set(-1.0);
		//sparkRight.set(1.0);
	}

	public void in(double speed) {
		//sparkLeft.set(speed);
		//sparkRight.set(-speed);
	}

	public void in() {
		//sparkLeft.set(1.0);
		//sparkRight.set(-1.0);
	}

	public void rotateIntake() {
		//sparkLeft.set(1.0);
		//sparkRight.set(1.0);
	}

	public void stopIntake() {
		sparkLeft.set(0.0);
		sparkRight.set(0.0);
	}

	public void updateShoulder() {
		SmartDashboard.putNumber("Shoulder Setpoint", shoulderSetpoint);
		System.out.println(-pid.calculate(getPot()));
		shoulder.set(ControlMode.Velocity, 0);
	}

	public void setShoulderAngle(double degrees) {
		this.angle = degrees;
		shoulderSetpoint = MathUtils.mapRange(-this.angle, 0, -90, ticksAt0, ticksAt90);
		pid.setSetpoint(shoulderSetpoint);
	}

	public boolean shoulderInTolerance(double tolerance) {
		return Math.abs(shoulder.getClosedLoopError(0)) <= tolerance;
	}

	public double getPot() {
		return shoulder.getSensorCollection().getAnalogIn();
	}

	public void toggleRotate() {
		if (angle == 90) {
			setShoulderAngle(0);
		} else if (angle == 0) {
			setShoulderAngle(90);
		} else { // default to down
			setShoulderAngle(0);
		}

	}
}
