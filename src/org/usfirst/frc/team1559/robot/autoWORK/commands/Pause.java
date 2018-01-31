package org.usfirst.frc.team1559.robot.autoWORK.commands;

import org.usfirst.frc.team1559.robot.autoWORK.AutoCommand;

public class Pause extends AutoCommand {

	private double moment;
	private double threshold;

	/**
	 * Creates a new pause command, which literally pauses the robot for the
	 * specified time
	 * 
	 * @param seconds
	 *            The time to pause the robot in seconds (i.e. 0.25 would be equate
	 *            to 250 milliseconds)
	 */
	public Pause(double seconds) {
		threshold = 50 * seconds;
	}

	@Override
	public void init() {
		// nothing?
	}

	@Override
	public void go() {
		moment++;
		isFinished = moment >= threshold;
	}

}
