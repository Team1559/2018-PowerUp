package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WPI_Spintake extends Command {

	private static double TIME_IN = 0.5;
	private static double TIME_OUT = 0.5;
	
	private boolean in;

	public WPI_Spintake(boolean in) {
		this.in = in;
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
		return this.timeSinceInitialized() >= (in ? TIME_IN : TIME_OUT);
	}
	
	@Override
	protected void end() {
		Robot.intake.stopIntake();
	}
}
