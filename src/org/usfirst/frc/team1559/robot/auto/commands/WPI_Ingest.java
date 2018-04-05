package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_Ingest extends CommandGroup {

	public WPI_Ingest() {
		this.addParallel(new WPI_CloseClaw());
		this.addSequential(new WPI_Spintake(true, .75)); //time was 0.5 then changed to .75
		this.addSequential(new WPI_Wait(0.25));
		this.addParallel(new WPI_RotateShoulder(90));
		this.addParallel(new WPI_Spintake(true, 1));
	}
	
}
