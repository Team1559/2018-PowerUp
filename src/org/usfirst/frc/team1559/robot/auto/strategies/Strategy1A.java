package org.usfirst.frc.team1559.robot.auto.strategies;

import java.util.ArrayList;

import org.usfirst.frc.team1559.robot.auto.AutoCommand;
import org.usfirst.frc.team1559.robot.auto.AutoSequence;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;
import org.usfirst.frc.team1559.robot.auto.commands.MoveForward;
import org.usfirst.frc.team1559.robot.auto.commands.MoveSideways;
import org.usfirst.frc.team1559.robot.auto.commands.Rotate;

public class Strategy1A extends AutoStrategy {

	@Override
	public void init() {
		sequences = new ArrayList<AutoSequence>();
		addSequence(new AutoSequence(new AutoCommand[] {
				new MoveForward(111, this),
				new MoveSideways(MoveSideways.LEFT, 72, this)
		}));
		addSequence(new AutoSequence(new AutoCommand[] {
				new Rotate(29.5, this),
				new MoveForward(146.4, this)
		}));
		addSequence(new AutoSequence(new AutoCommand[] {
				new MoveForward(115.5, this),
				new Rotate(90, this),
				new MoveForward(72, this),
				new Rotate(90, this),
				new MoveForward(12, this)
		}));
		addSequence(new AutoSequence(new AutoCommand[] {
				new Rotate(29.5, this),
				new MoveForward(146.4, this),
				new Rotate(-29.5, this),
				new MoveForward(4, this)
		}));
		isInitialized = true;
	}

}
