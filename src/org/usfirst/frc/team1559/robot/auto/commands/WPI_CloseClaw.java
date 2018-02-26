package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_CloseClaw extends Command {


	public WPI_CloseClaw() {
	}

	@Override
	protected void initialize() {
		Robot.intake.close();
	}
	

	@Override
	protected boolean isFinished() {
		return true;
	}
}
