package org.usfirst.frc.team1559.robot.auto.strategies;

import java.util.ArrayList;

import org.usfirst.frc.team1559.robot.auto.AutoCommand;
import org.usfirst.frc.team1559.robot.auto.AutoSequence;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;
import org.usfirst.frc.team1559.robot.auto.commands.MoveForward;
import org.usfirst.frc.team1559.robot.auto.commands.MoveSideways;
import org.usfirst.frc.team1559.robot.auto.commands.Rotate;

public class Strategy1B extends AutoStrategy {

	public Strategy1B() {
		init();
	}
	
	@Override
	public void init() {
		sequences = new ArrayList<AutoSequence>();
		addSequence(new AutoSequence(new AutoCommand[] {
				new MoveForward(111, this),
				new MoveSideways(MoveSideways.RIGHT, 44, this)
		}));
		addSequence(new AutoSequence(new AutoCommand[] {
				new Rotate(19.0, this),
				new MoveForward(134.9, this)
		}));
		addSequence(new AutoSequence(new AutoCommand[] {
				new MoveForward(115.5, this),
				new Rotate(90, this),
				new MoveForward(44, this),
				new Rotate(90, this),
				new MoveForward(12, this)
		}));
		addSequence(new AutoSequence(new AutoCommand[] {
				new Rotate(19, this),
				new MoveForward(134.9, this),
				new Rotate(-19, this),
				new MoveForward(4, this)
		}));
		isInitialized = true;
	}

}
