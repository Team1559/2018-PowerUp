package org.usfirst.frc.team1559.robot.autoWORK;

import java.util.ArrayList;

public abstract class AutoStrategy {

	public static final byte STARTING_POSITION_1 = 0;
	public static final byte STARTING_POSITION_2 = 1;
	public static final byte STARTING_POSITION_3 = 2;

	public byte startingPosition;

	public AutoSequence sequence;
	private ArrayList<AutoSequence> sequences;

	public AutoStrategy() {
		sequences = new ArrayList<AutoSequence>();
	}

	/**
	 * Adds a new sequence. Call this in {@link #init()}
	 * 
	 * @param sequence
	 *            The new sequence to add
	 */
	public void addSequence(AutoSequence sequence) {
		sequences.add(sequence);
	}

}
