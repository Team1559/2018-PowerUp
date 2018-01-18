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

	/**
	 * Sets {@link #old} to {@link #current}, and then sets {@link #current} to the
	 * given value
	 * 
	 * @param b
	 *            The value to set {@link #current} to
	 */
	public void update(boolean b) {
		old = current;
		current = b;
	}

	/**
	 * @return Whether or not this button is currently pressed
	 */
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
