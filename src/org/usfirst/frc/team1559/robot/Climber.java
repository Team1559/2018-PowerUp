package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Spark;

public class Climber {
	private Spark climber;

	public Climber(int port) {
		climber = new Spark(port);
	}

	public void startClimb() {
		climber.set(Constants.CLIMB_SPEED);
	}

	public void reverseClimb() {
		climber.set(-Constants.CLIMB_SPEED);
	}
	
	public void stopClimb() {
		climber.set(0);
	}
}
