package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Climber {
	/*
	 * If you have any questions about the code, ask for Michael Nersinger or
	 * Joshua Ingerowski from the Software Team.
	 * 
	 * The Climber subsystem uses a Solenoid to attach to the bar
	 * in end game. Part of the subsystem is allowing other robots to
	 * climb upon our robot, but as this is not a function that needs to be
	 * programmed, it is not referenced in this code. This code is used for the
	 * 2018 Game First Power Up. The type of climber used is a telescope which
	 * extends outward in smaller layers each time;
	 */
	private Solenoid climber;
	/**
	 * 
	 * @param port
	 * The number on the electrical board used for the Climber Solenoid.
	 **/
	public Climber(int port) {
		/**
		 * The Climber Constructor instantiates the field 'climber' at the
		 * specified port.
		 **/
		climber = new Solenoid(port);
	}

	public void extendClimb() {
		/**
		 * The startClimb method sets the field 'climber' to true.
		 * (This allows the climber to extend up and prepare to
		 * climb)
		 **/
		climber.set(true);
	}

	public void retractClimb() {
		/**
		 * The unClimb method sets the field 'climber' to false.
		 * (This causes the climber to retract and complete the
		 * climb)
		 **/
		climber.set(false);
	}

	private static Climber instance;
	/**
	 * 
	 * @return Returns the instance of the climber class.
	 */
	public static Climber getInstance() {
		/* 
		 * Returns the instance of the climber class.
		 */
		if (instance == null) {
			instance = new Climber(Wiring.CLIMBER_SOLENOID);
		}
		return instance;
	}

}
