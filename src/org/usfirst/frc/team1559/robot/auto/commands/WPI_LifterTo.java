package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_LifterTo extends Command {

	private int position;
	private static final int TOLERANCE = 10;

	public WPI_LifterTo(int position) {
		this.position = position;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.lifter.toPosition(position);
	}

	@Override
	protected boolean isFinished() {
		return Robot.lifter.isAtPosition(TOLERANCE);
	}

	@Override
	protected void end() {
	}

	@Override
	public String toString() {
		return "WPI_Ingest";
	}
}
