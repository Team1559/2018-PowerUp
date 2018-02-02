package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class Lifter {
	Potentiometer lift;
	AnalogInput ai;
	DigitalInput limit;
	Joystick joy;

	public Lifter(int a, int button) {
		ai = new AnalogInput(a);
		if (joy.getRawButton(button))
			lift = new AnalogPotentiometer(ai, 360, 30);
		else
			lift = new AnalogPotentiometer(ai, 0, 30);

	}
}
