package org.usfirst.frc.team1559.robot.auto.strategies;

import java.util.ArrayList;

import org.usfirst.frc.team1559.robot.auto.AutoSequence;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

public class Strategy1B extends AutoStrategy {

	public Strategy1B() {
		init();
	}
	
	@Override
	public void init() {
		sequences = new ArrayList<AutoSequence>();
		// forward 111 inches, sideways 44 inches to the right
		// rotate 19, forward 134.9 inches
		// forward 115.5 inches, rotate 90, forward 44 inches, rotate 90, forward 12 inches
		// rotate 19, forward 134.9 inches, rotate -19, forward 4 inches
		isInitialized = true;
	}

}
