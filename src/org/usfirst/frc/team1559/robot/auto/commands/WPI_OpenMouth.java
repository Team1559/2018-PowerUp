package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_OpenMouth extends CommandGroup {
	
	public WPI_OpenMouth() {
		this.addParallel(new WPI_RotateShoulder(0));
		this.addParallel(new WPI_OpenClaw());
	}
	
}
