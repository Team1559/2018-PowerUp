package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;

public class Climber {

	private Spark telescope;
	private Talon winch;

	public Climber() {
		telescope = new Spark(Wiring.CLM_SPARK); // Up and down telescope
		winch = new Talon(Wiring.CLM_TALON); // Winch function - the wheel that flips around
	}

	public void extendTelescope() {
		telescope.set(Constants.CLIMB_TELESCOPE_SPEED);
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
	
	public void reverseWinch() {
		winch.set(-Constants.CLIMB_WINCH_SPEED);
	}
}
