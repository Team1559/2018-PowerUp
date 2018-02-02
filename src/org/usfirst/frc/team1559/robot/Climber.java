package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;

public class Climber {
	private Spark climberSpark;
	private Talon climberTalon;
	private DigitalInput climberSparkLimit;

	public Climber() {
		climberSpark = new Spark(Wiring.CLIMBER_SPARK); // Up and down telescope
		climberTalon = new Talon(Wiring.CLIMBER_TALON); // Winch function - the wheel that flips around
		climberSparkLimit = new DigitalInput(Wiring.CLIMBER_LIMIT_ID);
	}

	public void extendTelescope() {
		if(!climberSparkLimit.get()) {
			climberSpark.set(Constants.CLIMB_TELESCOPE_SPEED);
		} else {
			climberSpark.set(0);
		}
	}

	public void retractTelescope() {
		climberSpark.set(-Constants.CLIMB_TELESCOPE_SPEED);
	}
	
	public void rotateWinch() {
		climberTalon.set(Constants.CLIMB_TALON_SPEED);
	}
	public void stopWinch() {
		climberTalon.set(0);
	}
}
