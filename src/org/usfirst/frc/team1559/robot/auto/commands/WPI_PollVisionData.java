package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_PollVisionData extends Command {

	public WPI_PollVisionData(double x, double y) {
	}

	@Override
	protected void initialize() {
		Robot.visionData.parseString(Robot.udp.get());
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	public String toString() {
		return String.format("PollVisionData()");
	}

}
