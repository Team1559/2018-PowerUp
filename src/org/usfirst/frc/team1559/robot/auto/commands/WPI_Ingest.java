package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_Ingest extends Command {

	private double time;

	public WPI_Ingest() {
		this.time = 1;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.intake.in();
		Robot.intake.rotateUp();
	}

	@Override
	protected boolean isFinished() {
		return timeSinceInitialized() > time;
	}

	@Override
	protected void end() {
	}

	@Override
	public String toString() {
		return "WPI_Ingest";
	}
}
