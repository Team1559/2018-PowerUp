package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.auto.AutoCommand;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

public class Rotate extends AutoCommand {

	public Rotate(double degrees, AutoStrategy parent) {
		this.parent = parent;
	}

	@Override
	public void init() {
		type = AutoCommand.TYPE_MOVE;
		isInitialized = true;
	}

	@Override
	public void iterate() {
		
	}

}
