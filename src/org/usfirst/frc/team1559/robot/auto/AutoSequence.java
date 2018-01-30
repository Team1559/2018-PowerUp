package org.usfirst.frc.team1559.robot.auto;

import java.util.ArrayList;

import org.usfirst.frc.team1559.robot.Robot;

/**
 * A collection of auto commands ({@link AutoCommand})
 * 
 * @author Evan Gartley (Victor Robotics Team 1559, Software)
 */
public class AutoSequence {

	// timer?

	public ArrayList<AutoCommand> commands;

	public boolean isDone;

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
	public AutoSequence(AutoCommand... commands) {
		this.commands = new ArrayList<AutoCommand>();
		for (AutoCommand command : commands) {
			this.commands.add(command);
		}
		i = 0;
		isDone = false;
	}

	public void execute() {
		if (i < commands.size()) {
			boolean initFrame = false;
			if (!commands.get(i).isInitialized) {
				commands.get(i).init();
				System.out.println(i + " INITIALIZED");
				initFrame = true;
			}
			commands.get(i).going();
			if (!initFrame) {
				if (commands.get(i).isFinished()) {
					System.out.println(i + " FINISHED");
					i++;
				}
			}
		
		} else {
			isDone = true;
		}
	}

	public void reset() {
		for (AutoCommand command : commands) {
			command.isInitialized = false;
		}
		i = 0;
		Robot.driveTrain.resetEncoders();
	}
}
