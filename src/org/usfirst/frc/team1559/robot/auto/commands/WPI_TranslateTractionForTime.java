package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_TranslateTractionForTime extends Command {

	private double speed;
	private double time;
	
	public WPI_TranslateTractionForTime(double speed, double time) {
		this.speed = speed;
		this.time = time;
	}
	
	public void execute() {
		Robot.driveTrain.drive(-speed, 0, 0);
	}
	
	public boolean isFinished() {
		return this.timeSinceInitialized() >= this.time;
	}
	
}
