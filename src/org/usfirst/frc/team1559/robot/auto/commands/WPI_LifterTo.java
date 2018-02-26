package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_LifterTo extends Command {

	private int position;
	private static final int TOLERANCE = 12;

	public WPI_LifterTo(int position) {
		this.position = position;
	}

	@Override
	protected void initialize() {
		Robot.lifter.setPosition(position);
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return Robot.lifter.isAtPosition(TOLERANCE);
	}

	@Override
	protected void end() {
		System.out.println(this + " IS FINISHED");
	}

	@Override
	public String toString() {
		return String.format("WPI_LifterTo(position=" + position + ")");
	}
}
