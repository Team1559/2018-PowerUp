package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_IngestNoSpin extends CommandGroup {

	public WPI_IngestNoSpin() {
		this.addParallel(new WPI_RotateShoulder(90));
		this.addSequential(new WPI_Wait(0.5));
		this.addSequential(new WPI_CloseClaw());
	}
	
}
