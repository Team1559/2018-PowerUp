package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Climber {
	private WPI_TalonSRX climber;

	public Climber(int port) {
		climber = new WPI_TalonSRX(port);
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

	private static Climber instance;

	public static Climber getInstance() {

		if (instance == null) {
			instance = new Climber(Wiring.CLIMBER_SOLENOID);
		}
		return instance;
	}

}
