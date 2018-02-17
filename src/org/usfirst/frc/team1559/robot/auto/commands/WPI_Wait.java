package org.usfirst.frc.team1559.robot.auto.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command that "pauses" the execution for a set amount of time, using WPI
 * time-based methods
 * 
 * @author Victor Robotics Team 1559, Software
 */
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
