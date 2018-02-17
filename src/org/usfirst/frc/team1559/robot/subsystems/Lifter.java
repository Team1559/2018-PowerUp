package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Lifter {

	private WPI_TalonSRX lifterMotor;
	private final int TIMEOUT = 0;
	private double kP = 7;
	private double kI = 0;
	private double kD = 0;
	private double kF = 0;
	
	private boolean isDown;
	

	public Lifter() {
		lifterMotor = new WPI_TalonSRX(Wiring.LIFT_TALON);
		lifterMotor.set(ControlMode.Position, 0);
		lifterMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);

		lifterMotor.configNominalOutputForward(0, TIMEOUT);
		lifterMotor.configNominalOutputReverse(0, TIMEOUT);
		lifterMotor.configPeakOutputForward(+1, TIMEOUT);
		lifterMotor.configPeakOutputReverse(-1, TIMEOUT);
		
		//lifterMotor.configPeakCurrentLimit(40, TIMEOUT); //raise the current limit of the boi

		lifterMotor.config_kP(0, kP, TIMEOUT);
		lifterMotor.config_kI(0, kI, TIMEOUT);
		lifterMotor.config_kD(0, kD, TIMEOUT);
		lifterMotor.config_kF(0, kF, TIMEOUT);

		lifterMotor.setSensorPhase(true);
		lifterMotor.setNeutralMode(NeutralMode.Brake);
	}

	//Positions negative, pot mounted backwards
	public double getPot() {
		return lifterMotor.getSensorCollection().getAnalogIn();
		//return lifterMotor.getSelectedSensorPosition(0);
	}

	public void toSwitch() {
		lifterMotor.set(ControlMode.Position, -Constants.SWITCH_TOP_LIMIT);
	}

	public void toBottomScale() {
		lifterMotor.set(ControlMode.Position, -Constants.SCALE_BOTTOM_LIMIT);
	}

	public void toNeutralScale() {
		lifterMotor.set(ControlMode.Position, -Constants.SCALE_NEUTRAL_LIMIT);
	}

	public void toTopScale() {
		lifterMotor.set(ControlMode.Position, -Constants.SCALE_TOP_LIMIT);
	}

	public void toHome() {
		lifterMotor.set(ControlMode.Position, -Constants.LIFT_BOTTOM_LIMIT);
	}
	
	public void driveUp() {
		if (getPot() > Constants.LIFT_TOP_LIMIT) 
			lifterMotor.set(ControlMode.PercentOutput, 0.5);
		else {
			stopMotor();
		}
	}
	
	public void driveDown() {
		if (getPot() < Constants.LIFT_BOTTOM_LIMIT)
			lifterMotor.set(ControlMode.PercentOutput, -0.5);
		else {
			stopMotor();
		}
	}

	public void setMotor(double value) {
		if (value > 0 && getPot() > Constants.LIFT_TOP_LIMIT)
			lifterMotor.set(ControlMode.PercentOutput, value);
		else if (value < 0 && getPot() < Constants.LIFT_BOTTOM_LIMIT) {
			lifterMotor.set(ControlMode.PercentOutput, value);
		}
		else {
			stopMotor();
		}
	}
	
	public void stopMotor() {
		lifterMotor.set(ControlMode.PercentOutput, 0);
	}
	
	public WPI_TalonSRX getMotor() {
		return lifterMotor;
	}
}
