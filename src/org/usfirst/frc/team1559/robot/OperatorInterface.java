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
	}
	
	public DTButton getDriverButton(int i) {
		return driverButtons[i];
	}
	
	public DTButton getCopilotButton(int i) {
		return copilotButtons[i];
	}
	
	public double getDriverX() {
		return driverStick.getX();
	}
	
	public double getDriverY() {
		return driverStick.getY();
	}
	
	public double getDriverZ() {
		return driverStick.getZ();
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
	
	public void update() {
		for (DTButton button : driverButtons) {
			button.update();
		}
		for (DTButton button : copilotButtons) {
			button.update();
		}
	}
}
