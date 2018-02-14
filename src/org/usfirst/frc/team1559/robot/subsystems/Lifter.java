package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Lifter {

	private WPI_TalonSRX lifterMotor;
	private final int TIMEOUT = 0;
	private double kP = 0.1;
	private double kI = 0;
	private double kD = 0;
	private double kF = 0;

	public Lifter() {
		lifterMotor = new WPI_TalonSRX(Wiring.LIFT_TALON);
		lifterMotor.set(ControlMode.Position, 0);
		lifterMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		
		lifterMotor.configNominalOutputForward(0, TIMEOUT);
		lifterMotor.configNominalOutputReverse(0, TIMEOUT);
		lifterMotor.configPeakOutputForward(+1, TIMEOUT);
		lifterMotor.configPeakOutputReverse(-1, TIMEOUT);

		lifterMotor.config_kP(0, kP, TIMEOUT);
		lifterMotor.config_kI(0, kI, TIMEOUT);
		lifterMotor.config_kD(0, kD, TIMEOUT);
		lifterMotor.config_kF(0, kF, TIMEOUT);
		
		lifterMotor.setSensorPhase(true);
		lifterMotor.setNeutralMode(NeutralMode.Brake);
	}

	public double getPot() {
		return lifterMotor.getSensorCollection().getAnalogIn();
	}
	
	public void toSwitch() {
		lifterMotor.set(ControlMode.Position, Constants.SWITCH_TOP_LIMIT);
	}
	public void toBottomScale() {
		lifterMotor.set(ControlMode.Position, Constants.SCALE_BOTTOM_LIMIT);

	}
	public void toNeutralScale() {
		lifterMotor.set(ControlMode.Position, Constants.SCALE_NEUTRAL_LIMIT);

	}
	public void toTopScale() {
		lifterMotor.set(ControlMode.Position, Constants.SCALE_TOP_LIMIT);

	}

	public void toHome() {
		lifterMotor.set(ControlMode.Position, Constants.LIFT_BOTTOM_LIMIT);
	}
}
