package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_OpenClaw extends Command {


	public WPI_OpenClaw() {
	}

	@Override
	protected void initialize() {
		Robot.intake.open();
	}
	

	@Override
	protected boolean isFinished() {
		return true;
	}
}
