package org.usfirst.frc.team1559.robot.auto;

import java.util.ArrayList;

/**
 * One or more auto sequences ({@link AutoSequence})
 * 
 * @author Evan Gartley (Victor Robotics Team 1559, Software)
 */
public abstract class AutoStrategy {

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
