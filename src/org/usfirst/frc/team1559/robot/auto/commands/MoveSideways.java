package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.auto.AutoCommand;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

/**
 * Moves the robot to the right or left, depending on the given direction
 * 
 * @author Victor Robotics Team 1559, Software
 * @see #LEFT
 * @see #RIGHT
 */
public class MoveSideways extends AutoCommand {

	/**
	 * Leftward direction (relative to the front of the robot)
	 */
	public static final int LEFT = 0;
	/**
	 * Rightward direction (relative to the front of the robot)
	 */
	public static final int RIGHT = 1;

	/**
	 * Which direction (left or right) the robot will be moved
	 * 
	 * @see #LEFT
	 * @see #RIGHT
	 */
	public int direction;

	public MoveSideways(int direction, double distance, AutoStrategy parent) {
		this.direction = direction;
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
