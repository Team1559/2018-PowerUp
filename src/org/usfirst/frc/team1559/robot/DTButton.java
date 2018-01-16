package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

public class DTButton {
	
	boolean old, current;
	Joystick stick;
	int button;
	
	public DTButton(Joystick stick, int button) {
		this.stick = stick;
		this.button = button;
	}
	
	public void update() {
		update(stick.getRawButton(button));
	}

	public void update(boolean b) {
		old = current;
		current = b;
	}	
	
	public boolean isPressed() {
		return current && !old;
	}
	
	public boolean isDown() {
		return current;
	}
	
	public boolean isReleased() {
		return old && !current;
	}
}
