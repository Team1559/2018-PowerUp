package org.usfirst.frc.team1559.robot.auto;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A collection of commands that should run chronologically
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class AutoSequence {

	public ArrayList<Command> commands;

	public boolean isDone;
	public int i;

	public AutoSequence(Command... commands) {
		this.commands = new ArrayList<Command>();
		for (Command command : commands) {
			this.commands.add(command);
		}
	}
	
}
