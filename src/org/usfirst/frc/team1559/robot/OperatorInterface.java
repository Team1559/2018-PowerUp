package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

	public Joystick driverStick, copilotStick;
	private DTButton[] driverButtons, copilotButtons;

	public OperatorInterface() {
		driverStick = new Joystick(Wiring.JOY_DRIVER);
		copilotStick = new Joystick(Wiring.JOY_COPILOT);
		driverButtons = new DTButton[driverStick.getButtonCount()];
		copilotButtons = new DTButton[copilotStick.getButtonCount()];
		for (int i = 0; i < driverButtons.length; i++) {
			driverButtons[i] = new DTButton(driverStick, i + 1);
		}
		for (int i = 0; i < copilotButtons.length; i++) {
			copilotButtons[i] = new DTButton(copilotStick, i + 1);
		}
	}

	public DTButton getDriverButton(int i) {
		return driverButtons[i];
	}

	public DTButton getCopilotButton(int i) {
		return copilotButtons[i];
	}

	public double getDriverX() {
		return driverStick.getRawAxis(0);
	}

	public double getDriverY() {
		return driverStick.getRawAxis(1);
	}

	public double getDriverZ() {
		return driverStick.getRawAxis(4);
	}

	public double getCopilotX() {
		return copilotStick.getX();
	}

	public double getCopilotY() {
		return copilotStick.getY();
	}

	public double getCopilotZ() {
		return copilotStick.getZ();
		
	}

	/**
	 * Calls the update methods for all of the driver and co-pilot buttons
	 * ({@link #driverButtons} and {@link #copilotButtons})
	 */
	public void update() {
		for (DTButton button : driverButtons) {
			button.update();
		}
		for (DTButton button : copilotButtons) {
			button.update();
		}
	}
}
