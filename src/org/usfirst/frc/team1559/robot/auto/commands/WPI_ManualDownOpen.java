package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_ManualDownOpen extends CommandGroup {

	public WPI_ManualDownOpen() {
		//this.addSequential(new WPI_OpenClaw());
		this.addSequential(new WPI_RotateShoulder(0));
		this.addSequential(new WPI_Wait(0.25));
		this.addSequential(new WPI_OpenClaw());
	}
}
