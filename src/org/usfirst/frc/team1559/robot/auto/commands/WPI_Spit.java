package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_Spit extends CommandGroup {
	
	public WPI_Spit() {
		this.addSequential(new WPI_RotateShoulder(0));
		this.addSequential(new WPI_Spintake(false, 0.5));
		this.addSequential(new WPI_OpenMouth()); // this is new, probably bad, actually its not bad
	}
	
}
