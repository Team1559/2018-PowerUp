package org.usfirst.frc.team1559.robot.auto;

import java.util.ArrayList;

/**
 * A collection of auto commands ({@link AutoCommand})
 * 
 * @author Evan Gartley (Victor Robotics Team 1559, Software)
 */
public class AutoSequence {

	// timer?

	public ArrayList<AutoCommand> commands;

	/**
	 * The index of the current {@link AutoCommand} that is running in
	 * {@link #commands}
	 */
	public int i;

	/**
	 * Create a new auto sequence
	 * 
	 * @param commands
	 */
	public AutoSequence(AutoCommand[] commands) {
		this.commands = new ArrayList<AutoCommand>();
		for (AutoCommand command : commands)
			this.commands.add(command);
	}

}
