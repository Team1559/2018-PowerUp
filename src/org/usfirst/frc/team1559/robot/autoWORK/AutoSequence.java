package org.usfirst.frc.team1559.robot.autoWORK;

import java.util.ArrayList;

public class AutoSequence {

	public AutoCommand currentCommand;
	private ArrayList<AutoCommand> commands;
	
	public int amountOfCommands;
	public boolean isComplete;
	
	private int currentCommandIndex = -1;
	
	public AutoSequence(AutoCommand... commands) {
		this.commands = new ArrayList<AutoCommand>();
		for (AutoCommand c : commands)
			this.commands.add(c);
		amountOfCommands = this.commands.size();
	}
	
	private void nextCommand() {
		currentCommandIndex++;
		currentCommand = commands.get(currentCommandIndex);
		currentCommand.init();
	}
	
	public void go() {
		if (isComplete == false) {
			if (currentCommandIndex == -1) {
				nextCommand();
				return;
			}
			currentCommand.go();
			if (currentCommand.isFinished == true) {
				if (currentCommandIndex + 1 < commands.size()) {
					nextCommand();
				} else {
					System.out.println("The auto sequence has completed all of its commands");
					isComplete = true;
				}
			}
		}
	}
	
}
