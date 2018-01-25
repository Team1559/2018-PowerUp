package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.auto.AutoCommand;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

/**
 * Moves the command forward (or backward if given a negative distance)
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class MoveForward extends AutoCommand {

	public MoveForward(double distance, AutoStrategy parent) {
		this.parent = parent;
	}

	@Override
	public void init() {
		type = AutoCommand.TYPE_MOVE;
		isInitialized = true;
	}

	@Override
	public void going() {
		if (isInitialized == false) {
			// need to initialize!
		}
		if (parent.isInitialized == false) {
			// need to initialize!
		}
	}

}
