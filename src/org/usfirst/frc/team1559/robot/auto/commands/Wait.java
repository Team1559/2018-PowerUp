package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Debug;
import org.usfirst.frc.team1559.robot.auto.AutoCommand;

public class Wait extends AutoCommand {

	private int counter = 0;
	private int limit;
	private int time;

	/**
	 * Creates a new wait command for the specified amount of time
	 * 
	 * @param time
	 *            The amount of time to wait, in seconds
	 */
	public Wait(int time) {
		this.time = time;
		limit = this.time * 50;
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void iterate() {
		counter++;
		// check if done
		// *****************************
		isDone = counter >= limit;
		// *****************************
		if (isDone) {
//			Debug.out("The Wait command, of " + time + " seconds (" + parent.sequences.get(0).i + " of "
//					+ parent.sequences.get(0).commands.size() + " commands within the current sequence) has finished");
		}
	}

}
