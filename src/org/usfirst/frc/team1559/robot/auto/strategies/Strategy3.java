package org.usfirst.frc.team1559.robot.auto.strategies;

import java.util.ArrayList;

import org.usfirst.frc.team1559.robot.auto.AutoCommand;
import org.usfirst.frc.team1559.robot.auto.AutoSequence;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

public class Strategy3 extends AutoStrategy {

	@Override
	public void init() {
		sequences = new ArrayList<AutoSequence>();
		addSequence(new AutoSequence(new AutoCommand[] {
				// 127.5 inches forward, 16 inches to the right
		}));
	}

}
