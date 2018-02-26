package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_Spintake extends Command {
	
	private boolean in;
	private double time;

	public WPI_Spintake(boolean in, double time) {
		this.in = in;
		this.time = time;
	}

	@Override
	protected void initialize() {
		if (in) {
			Robot.intake.in();
		} else {
			Robot.intake.out();
		}
	}
	

	@Override
	protected boolean isFinished() {
		return this.timeSinceInitialized() >= time;
	}
	
	@Override
	protected void end() {
		Robot.intake.stopIntake();
	}
}
