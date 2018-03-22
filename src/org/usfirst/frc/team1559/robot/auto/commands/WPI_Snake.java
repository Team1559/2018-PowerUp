package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WPI_Snake extends CommandGroup {

	public WPI_Snake(double x, double angle) {
		this.addParallel(new WPI_RotateAbs(angle, false));
		this.addSequential(new WPI_TractionSpinlate(x, angle));
	}
	
}
