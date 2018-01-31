package org.usfirst.frc.team1559.robot.auto;

import java.util.ArrayList;

import org.usfirst.frc.team1559.robot.Debug;
import org.usfirst.frc.team1559.robot.Robot;

/**
 * A collection of auto commands ({@link AutoCommand})
 * 
 * @author Evan Gartley (Victor Robotics Team 1559, Software)
 */
public class AutoSequence {

	public ArrayList<AutoCommand> commands;

	public boolean isDone;
	public int i;

	public AutoSequence(AutoCommand... commands) {
		this.commands = new ArrayList<AutoCommand>();
		for (AutoCommand command : commands) {
			this.commands.add(command);
		}
	}

	/**
	 * This should be called in autonomousPeriodic()
	 */
	public void execute() {
		if (!isDone) {
			// failsafe check for isDone, but should not be called when isDone is true
			// anyway
			AutoCommand command = commands.get(i);
			if (!command.isInitialized) {
				Debug.out("Command " + (i + 1) + " of " + commands.size() + " has been initialized");
				command.init();
			}
			command.going();
			if (command.isDone) {
				Debug.out("Switching to the next command (from " + i + " to " + (i + 1) + ")");
				i++;
				if (i == commands.size()) {
					Debug.out("All the commands have finished! (total of " + commands.size() + ")");
					isDone = true;
				}
			}
		}
	}

	public void reset() {
		Debug.out("Resetting sequence...");
		byte k = 1;
		for (AutoCommand command : commands) {
			Debug.out("Command " + k + " of " + commands.size() + " has been de-initialized");
			k++;
			command.isInitialized = false;
		}
		Debug.out("i has been set to zero");
		i = 0;
		Debug.out("Encoders have been reset");
		Robot.driveTrain.resetEncoders();
	}
}
