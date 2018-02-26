package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lifter {

	private WPI_TalonSRX lifterMotor;
	private static final int TIMEOUT = 0;
	private double kP = 25;
	private double kI = 0;
	private double kD = 0;// 5
	private double kF = 0;
	private double setpoint;

	private boolean manualControl;
	private double output;

	public Lifter() {
		lifterMotor = new WPI_TalonSRX(Wiring.LIFT_TALON);
		lifterMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);

		lifterMotor.configClosedloopRamp(0.2, TIMEOUT);
		lifterMotor.configPeakCurrentLimit(40, TIMEOUT);
		lifterMotor.configContinuousCurrentLimit(20, TIMEOUT);
		lifterMotor.enableCurrentLimit(true);

		lifterMotor.configNominalOutputForward(+0.25, TIMEOUT);
		lifterMotor.configNominalOutputReverse(-0, TIMEOUT);
		lifterMotor.configPeakOutputForward(+1, TIMEOUT);
		lifterMotor.configPeakOutputReverse(-.45, TIMEOUT);

		lifterMotor.configReverseSoftLimitThreshold(Constants.LIFT_LOWER_BOUND, TIMEOUT);
		lifterMotor.configForwardSoftLimitThreshold(Constants.LIFT_UPPER_BOUND, TIMEOUT);
		lifterMotor.configForwardSoftLimitEnable(true, TIMEOUT);
		lifterMotor.configReverseSoftLimitEnable(true, TIMEOUT);

		lifterMotor.config_kP(0, kP, TIMEOUT);
		lifterMotor.config_kI(0, kI, TIMEOUT);
		lifterMotor.config_kD(0, kD, TIMEOUT);
		lifterMotor.config_kF(0, kF, TIMEOUT);

		lifterMotor.setSensorPhase(false);
		lifterMotor.setNeutralMode(NeutralMode.Brake);

		lifterMotor.enableVoltageCompensation(false);

		setpoint = Constants.LIFT_P1_TICKS;
		manualControl = false;
		output = 0;
	}

	// Positions are negative, pot mounted backwards
	public double getPot() {
		return lifterMotor.getSensorCollection().getAnalogIn();
		// return lifterMotor.getSelectedSensorPosition(0); //this one might be better
	}

	public void update() {
		if (manualControl) {
			lifterMotor.set(ControlMode.PercentOutput, output);
		} else {
			lifterMotor.set(ControlMode.Position, setpoint);
		}
		SmartDashboard.putNumber("Lifter Motor Voltage", lifterMotor.getMotorOutputVoltage());
	}

	public void setPosition(int pos) {
		manualControl = false;
		if (pos == 1) {
			setpoint = Constants.LIFT_P1_TICKS;
		} else if (pos == 15) {
			setpoint = Constants.LIFT_P1_5_TICKS;
		} else if (pos == 2) {
			setpoint = Constants.LIFT_P2_TICKS;
		} else if (pos == 3) {
			setpoint = Constants.LIFT_P3_TICKS;
		} else if (pos == 4) {
			setpoint = Constants.LIFT_P4_TICKS;
		} else if (pos == 5) {
			setpoint = Constants.LIFT_P5_TICKS;
		}
	}

	public boolean isAtPosition(int tolerance) {
		return Math.abs(lifterMotor.getClosedLoopError(0)) <= tolerance;
	}

	public void driveUp() {
		// if (getPot() > Constants.LIFT_UPPER_BOUND)
		lifterMotor.set(ControlMode.PercentOutput, 0.5);
		// else {
		// stopMotor();
		// }
	}

	public void holdPosition() {
		lifterMotor.set(ControlMode.Position, getPot());
	}

	public void driveDown() {
		// if (getPot() < Constants.LIFT_LOWER_BOUND)
		lifterMotor.set(ControlMode.PercentOutput, -0.5);
		// else {
		// stopMotor();
		// }
	}

	public void setMotor(double value) {
		// if (value > 0 && getPot() < Constants.LIFT_UPPER_BOUND)
		lifterMotor.set(ControlMode.PercentOutput, value);
		// else if (value < 0 && getPot() > Constants.LIFT_LOWER_BOUND) {
		// lifterMotor.set(ControlMode.PercentOutput, value);
		// }
		// else {
		// stopMotor();
		// }
	}

	public void stopMotor() {
		lifterMotor.set(ControlMode.PercentOutput, 0);
	}

	public WPI_TalonSRX getMotor() {
		return lifterMotor;
	}
	
	public void setManual(double value) {
		manualControl = true;
		output = value;
	}
}
