package org.usfirst.frc.team1559.robot.auto.strategies;

import java.util.ArrayList;

import org.usfirst.frc.team1559.robot.auto.*;
import org.usfirst.frc.team1559.robot.auto.commands.*;

public class Strategy2 extends AutoStrategy {

	public int direction;

	/**
	 * Creates the 2nd strategy
	 * 
	 * @param direction
	 *            One of the two constants from
	 *            {@link org.usfirst.frc.team1559.robot.auto.commands.MoveSideways
	 *            MoveSideways}
	 * @see {@link org.usfirst.frc.team1559.robot.auto.commands.MoveSideways#LEFT
	 *      MoveSideways.LEFT}
	 * @see {@link org.usfirst.frc.team1559.robot.auto.commands.MoveSideways#RIGHT
	 *      MoveSideways.RIGHT}
	 */
	public Strategy2(int direction) {
		this.direction = direction;
		init();
	}

	@Override
	public void init() {
		sequences = new ArrayList<AutoSequence>();
		addSequence(new AutoSequence(
				new AutoCommand[] { new MoveForward(127.5, this), new MoveSideways(direction, 16, this) }));
		isInitialized = true;
	}

}
