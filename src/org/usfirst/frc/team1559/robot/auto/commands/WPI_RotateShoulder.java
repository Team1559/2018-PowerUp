package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_RotateShoulder extends Command {

	private double angle;
	
	public WPI_RotateShoulder(double angle) {
		this.angle = angle;
	}

	@Override
	protected void initialize() {
		Robot.intake.setShoulderAngle(angle);
	}

	@Override
	protected boolean isFinished() {
		//return Robot.intake.shoulderInTolerance(300);
		return true;
	}
	
	@Override
	protected void end() {
	}
}
