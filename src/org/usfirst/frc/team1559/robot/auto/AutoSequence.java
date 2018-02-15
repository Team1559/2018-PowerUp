package org.usfirst.frc.team1559.robot.auto;

import java.util.ArrayList;

import org.usfirst.frc.team1559.robot.Robot;

/**
 * A collection of auto commands ({@link AutoCommand})
 * 
 * @author Victor Robotics Team 1559, Software
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
				System.out.println("Command " + (i + 1) + " of " + commands.size() + " has been initialized");
				command.init();
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
		System.out.println("Resetting auto sequence...");
		byte k = 0;
		for (AutoCommand command : commands) {
			System.out.println("Command " + k + " of " + commands.size() + " has been de-initialized");
			k++;
			command.isInitialized = false;
		}
		System.out.println("i has been set to zero");
		i = 0;
		System.out.println("Encoders have been reset");
		Robot.driveTrain.resetQuadEncoders();
	}
}
