package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Lifter {

	private Potentiometer potentiometer;
	private WPI_TalonSRX motor;

	public Lifter() {
		potentiometer = new AnalogPotentiometer(Wiring.LFT_POT);
		motor = new WPI_TalonSRX(Wiring.LFT_TALON);
	}

	public double getPot() {
		return motor.getSensorCollection().getAnalogIn();
	}
	
	public void toSwitch() {
		motor.set(ControlMode.Position, Constants.SWITCH_TOP_LIMIT);
	}
	public void toBottomScale() {
		motor.set(ControlMode.Position, Constants.SCALE_BOTTOM_LIMIT);

	}
	public void toNeutralScale() {
		motor.set(ControlMode.Position, Constants.SCALE_NEUTRAL_LIMIT);

	}
	public void toTopScale() {
		motor.set(ControlMode.Position, Constants.SCALE_TOP_LIMIT);
	}

	public void goDown() {
		if (potentiometer.get() > Constants.LIFT_BOTTOM_LIMIT && potentiometer.get() >= Wiring.BTN_LIFT_GROUND) {
			motor.set(-Constants.LIFT_SPEED);
		} else {
			motor.set(0);
		}

	}
}
