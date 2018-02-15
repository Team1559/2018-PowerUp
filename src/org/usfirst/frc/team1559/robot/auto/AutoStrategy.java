package org.usfirst.frc.team1559.robot.auto;

/**
 * An {@link AutoSequence}
 * 
 * @author Victor Robotics Team 1559, Software
 */
public abstract class AutoStrategy {

	public static final int STARTING_POSITION_1 = 1;
	public static final int STARTING_POSITION_2 = 2;
	public static final int STARTING_POSITION_3 = 3;

	/**
	 * The starting position, which will be either a 1, 2 or 3 (use the constants
	 * below)
	 * 
	 * @see #STARTING_POSITION_1
	 * @see #STARTING_POSITION_2
	 * @see #STARTING_POSITION_3
	 */
	public int startingPosition;
	/**
	 * The ({@link AutoSequence}) to run
	 */
	public AutoSequence sequence;

}
