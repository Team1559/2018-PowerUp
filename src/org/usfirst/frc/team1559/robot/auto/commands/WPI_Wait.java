package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_Wait extends Command {

	private double time;
	
	public WPI_Wait(double seconds) {
		this.time = seconds;
	}

	@Override
	protected boolean isFinished() {
		System.out.println(this + " has finished");
		return timeSinceInitialized() > time;
	}

	@Override
	public String toString() {
		return String.format("Wait(%f seconds)", time);
	}
}
