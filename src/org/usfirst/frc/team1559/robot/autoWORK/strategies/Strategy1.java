package org.usfirst.frc.team1559.robot.autoWORK.strategies;

import org.usfirst.frc.team1559.robot.autoWORK.AutoSequence;
import org.usfirst.frc.team1559.robot.autoWORK.AutoStrategy;
import org.usfirst.frc.team1559.robot.autoWORK.commands.MecanumMove;
import org.usfirst.frc.team1559.robot.autoWORK.commands.Pause;

public class Strategy1 extends AutoStrategy {

	public Strategy1() {
		super();
		addSequence(new AutoSequence(new MecanumMove(80, 0), new Pause(2), new MecanumMove(-80, 0)));
	}

}
