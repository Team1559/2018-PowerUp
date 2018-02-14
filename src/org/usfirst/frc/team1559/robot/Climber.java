package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;

public class Climber {

	private Spark telescope;
	private Talon winch;
	private DigitalInput limitSwitch;

	public Climber() {
		telescope = new Spark(Wiring.CLM_SPARK); // Up and down telescope
		winch = new Talon(Wiring.CLM_TALON); // Winch function - the wheel that flips around
		limitSwitch = new DigitalInput(Wiring.CLM_LIMIT_ID);
	}

	public void extendTelescope() {
		if (!limitSwitch.get()) {
			telescope.set(Constants.CLIMB_TELESCOPE_SPEED);
		} else {
			telescope.set(0);
		}
	}

	public void retractTelescope() {
		telescope.set(-Constants.CLIMB_TELESCOPE_SPEED);
	}

	public void rotateWinch() {
		winch.set(Constants.CLIMB_WINCH_SPEED);
	}

	public void stopWinch() {
		winch.set(0);
	}
}
