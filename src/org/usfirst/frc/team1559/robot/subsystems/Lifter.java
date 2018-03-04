package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.util.MathUtils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Lifter {
	
	private static final double[] POSITIONS_INCHES = { 8.75, 29.5, 61.9, 73.9, 85.9 };
	private static final double POSITION_BOT_INCHES = 8.75;
	private static final double POSITION_TOP_INCHES = 80.5;

	private static final int RANGE = 529; // difference between up and down in ticks //POS FOR ROBOT 2
	public int lowerBound = 267+7; //255 FOR ROBOT 2
	public int upperBound;
	

	private double[] positionsTicks = new double[POSITIONS_INCHES.length];

	private WPI_TalonSRX lifterMotor;
	private static final int TIMEOUT = 0;
	private double kP = 25;
	private double kI = 0;
	private double kD = 0;// 5
	private double kF = 0;
	private double setpoint;

	public Lifter() {
		calculatePositions();

		lifterMotor = new WPI_TalonSRX(Wiring.LIFT_TALON);
		lifterMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);

		lifterMotor.configClosedloopRamp(0.2, TIMEOUT);
		lifterMotor.configPeakCurrentLimit(40, TIMEOUT);
		lifterMotor.configContinuousCurrentLimit(22, TIMEOUT); //24 skipped, 22 didnt, TODO try 23 
		lifterMotor.enableCurrentLimit(true);

		lifterMotor.configNominalOutputForward(+0.25, TIMEOUT);
		lifterMotor.configNominalOutputReverse(-0, TIMEOUT);
		lifterMotor.configPeakOutputForward(+1, TIMEOUT);
		lifterMotor.configPeakOutputReverse(-.45, TIMEOUT); //-.45 for robot 2

		lifterMotor.configReverseSoftLimitThreshold(lowerBound, TIMEOUT);
		lifterMotor.configForwardSoftLimitThreshold(upperBound, TIMEOUT);
		lifterMotor.configForwardSoftLimitEnable(true, TIMEOUT);
		lifterMotor.configReverseSoftLimitEnable(true, TIMEOUT);

		lifterMotor.config_kP(0, kP, TIMEOUT);
		lifterMotor.config_kI(0, kI, TIMEOUT);
		lifterMotor.config_kD(0, kD, TIMEOUT);
		lifterMotor.config_kF(0, kF, TIMEOUT);

		lifterMotor.setSensorPhase(false); //false on robot 2
		lifterMotor.setNeutralMode(NeutralMode.Brake);
		lifterMotor.enableVoltageCompensation(false);

		setpoint = positionsTicks[0];
	}

	public double getPot() {
		//return lifterMotor.getSensorCollection().getAnalogIn();
		return lifterMotor.getSelectedSensorPosition(0);
	}
	
	public void driveManual(double val) {
		if(val >= 0) {
			setpoint -= 3*val;
		}
		else if (val <= 0) {
			setpoint -= 5*val;
		}
//		if(val >= 0) {
//			setpoint += 5*val;
//		}
//		else if (val <= 0) {
//			setpoint += 3*val;
//		}
	}

	public void update() {
		lifterMotor.set(ControlMode.Position, setpoint); //setpoint is pos for robot 2
	}

	public void setPosition(int pos) {
		pos -= 1;	
		setpoint = positionsTicks[pos];
	}

	public boolean isAtPosition(int tolerance) {
		return Math.abs(lifterMotor.getClosedLoopError(0)) <= tolerance;
	}

	public void setMotor(double value) {
		lifterMotor.set(ControlMode.PercentOutput, value);
	}

	public WPI_TalonSRX getMotor() {
		return lifterMotor;
	}

	private void calculatePositions() {
		upperBound = lowerBound + RANGE;
		int n = POSITIONS_INCHES.length;
		for (int i = 0; i < n; i++) {
			positionsTicks[i] = MathUtils.mapRange(POSITIONS_INCHES[i], POSITION_BOT_INCHES, POSITION_TOP_INCHES,
					lowerBound, upperBound);
		}
	}
	
	public void reset() {
		lowerBound = (int) getPot();
		calculatePositions();
	}
}
