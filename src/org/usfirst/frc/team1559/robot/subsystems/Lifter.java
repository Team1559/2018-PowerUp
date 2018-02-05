package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

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

	public void liftUp() {
		if (potentiometer.get() < Constants.LIFT_TOP_LIMIT) {
			motor.set(Constants.LIFT_SPEED);
		} else {
			motor.set(0);
		}

	}

	public void goDown() {
		if (potentiometer.get() > Constants.LIFT_BOTTOM_LIMIT) {
			motor.set(-Constants.LIFT_SPEED);
		} else {
			motor.set(0);
		}

	}
}
