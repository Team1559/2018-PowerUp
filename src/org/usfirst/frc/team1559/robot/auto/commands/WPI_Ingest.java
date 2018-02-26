package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_Ingest extends CommandGroup {

	public WPI_Ingest() {
		this.addParallel(new WPI_CloseClaw());
		this.addSequential(new WPI_Spintake(true));
		this.addSequential(new WPI_RotateShoulder(true));
	}
	
}
