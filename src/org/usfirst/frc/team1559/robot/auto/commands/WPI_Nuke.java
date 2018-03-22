package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WPI_Nuke extends Command {

	private double time;
	
	//ejects all cubes at end of auto
	public WPI_Nuke() {
		this.time = 15;
	}

	@Override
	protected boolean isFinished() {
		System.out.println("all cubes removed");
		return timeSinceInitialized() > time-0.2;
	}

	@Override
	public String toString() {
		return String.format("Wait(%f seconds)", time);
	}
	
	@Override
	protected void end() {
		Robot.intake.rotateDown();
		new WPI_Spit().start();
	}
}
