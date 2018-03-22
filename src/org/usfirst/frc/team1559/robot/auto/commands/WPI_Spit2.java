package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_Spit2 extends CommandGroup {
	
	public WPI_Spit2() {
		this.addParallel(new WPI_Spintake(false, 0.8, 0.8169));
		CommandGroup waitRotate = new CommandGroup();
		waitRotate.addSequential(new WPI_Wait(0.15));
		waitRotate.addSequential(new WPI_RotateShoulder(false));
		this.addSequential(waitRotate);
		this.addSequential(new WPI_OpenMouth()); //this is new, probably bad, actually its not bad
	}
	
}
