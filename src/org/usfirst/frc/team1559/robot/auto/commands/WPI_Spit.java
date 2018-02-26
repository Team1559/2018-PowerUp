package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_Spit extends CommandGroup {
	
	public WPI_Spit() {
		this.addSequential(new WPI_RotateShoulder(false));
		this.addSequential(new WPI_Spintake(false));
		this.addSequential(new WPI_OpenMouth());
	}
	
}
