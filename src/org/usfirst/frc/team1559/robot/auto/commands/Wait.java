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
		counter = 0;
	}

	@Override
	protected void iterate() {
		counter++;
	}

	@Override
	public boolean isFinished() {
		return counter >= limit;
	}

}
