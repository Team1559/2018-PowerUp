package org.usfirst.frc.team1559.robot.auto;
//wheel megers, ruuyn lii, jen burrow-duneske
import java.util.ArrayList;

/**
 * One or more auto sequences ({@link AutoSequence})
 * 
 * @author Victor Robotics Team 1559, Software
 */
public abstract class AutoStrategy {

	public static final int STARTING_POSITION_1 = 0;
	public static final int STARTING_POSITION_2 = 0;
	public static final int STARTING_POSITION_3 = 0;

	/**
	 * The starting position, which will be wither a 1, 2 or 3 (use the constants
	 * below)
	 * 
	 * @see #STARTING_POSITION_1
	 * @see #STARTING_POSITION_2
	 * @see #STARTING_POSITION_3
	 */
	public int startingPosition;
	
	/**
	 * Whether or not the strategy has been initialized <br>
	 * <br>
	 * This should be set to <code>true</code> at the end of {@link #init()}
	 */
	public boolean isInitialized;
	/**
	 * Collection of sequences ({@link AutoSequence})
	 */
	public ArrayList<AutoSequence> sequences;

	/**
	 * Initializes the strategy, which should includes its commands' initializations
	 * (via {@link #addSequence(AutoSequence)})
	 */
	public abstract void init();

	/**
	 * Adds the given auto sequence ({@link AutoSequence}) to {@link #sequences}
	 * 
	 * @param sequence
	 *            The auto sequence to add to {@link #sequences}
	 */
	public void addSequence(AutoSequence sequence) {
		sequences.add(sequence);
	}

}
