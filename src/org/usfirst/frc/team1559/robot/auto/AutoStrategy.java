package org.usfirst.frc.team1559.robot.auto;

import java.util.ArrayList;

/**
 * One or more auto sequences ({@link AutoSequence})
 * 
 * @author Evan Gartley (Victor Robotics Team 1559, Software)
 */
public class AutoStrategy {

	public int id;
	public ArrayList<AutoSequence> sequences;
	
	public AutoStrategy(int id) {
		this.id = id;
		sequences = new ArrayList<AutoSequence>();
	}
	
	public void addSequence(AutoSequence sequence) {
		sequences.add(sequence);
	}
	
}
