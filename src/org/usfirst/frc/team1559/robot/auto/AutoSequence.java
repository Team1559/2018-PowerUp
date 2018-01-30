package org.usfirst.frc.team1559.robot.auto;

import java.util.ArrayList;

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
				System.out.println("Initializing all of the commands...");
				byte k = 1;
				for (AutoCommand c : commands) {
					c.init();
					System.out.println("Command " + k + " of " + commands.size() + " has been initialized");
					k++;
					// this should not be called again, because all of the commands should have
					// isInitialized set to true
				}
			}
			command.going();
			if (command.isDone) {
				System.out.println("Switching to the next command (from " + i + " to " + (i + 1) + ")");
				i++;
				if (i == commands.size()) {
					System.out.println("All the commands have finished! (total of " + commands.size() + ")");
					isDone = true;
				}
			}
		}
	}

	public void reset() {
		System.out.println(
				"Resetting sequence... (WARNING: all commands will have isInitialized set to false and i will be set to 0!)");
		for (AutoCommand command : commands) {
			command.isInitialized = false;
		}
		i = 0;
		Robot.driveTrain.resetEncoders();
	}
}
