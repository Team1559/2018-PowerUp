package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.auto.AutoCommand;

public class Wait extends AutoCommand {

	private int counter = 0;
	private int limit;
	
	public Wait(int time) {
		limit = time * 50;
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void iterate() {
		counter++;
		// check if done
		// *****************************
		isDone = counter >= limit;
		// *****************************
	}

}
