package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_RotateShoulder extends Command {

	private static double TIME_DOWN = 1;
	private static double TIME_UP = 1;
	
	private boolean up;

	public WPI_RotateShoulder(boolean up) {
		this.up = up;
	}

	@Override
	protected void initialize() {
		if (up) {
			Robot.intake.rotateUp();
		} else {
			Robot.intake.rotateDown();
		}
		Robot.intake.setActiveRotate(true);
	}

	@Override
	protected boolean isFinished() {
		return this.timeSinceInitialized() >= (up ? TIME_UP : TIME_DOWN);
	}
	
	@Override
	protected void end() {
		Robot.intake.setActiveRotate(false);
	}
}
