package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_ManualShoulderRotate extends CommandGroup {

	public WPI_ManualShoulderRotate(boolean b) {
		this.addSequential(new WPI_RotateShoulder(b));
	}	
}
