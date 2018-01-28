package org.usfirst.frc.team1559.robot.auto.strategies;

import java.util.ArrayList;

import org.usfirst.frc.team1559.robot.auto.AutoSequence;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;
import org.usfirst.frc.team1559.robot.auto.commands.MecanumTranslate;
import org.usfirst.frc.team1559.robot.auto.commands.Wait;

public class Strategy1A extends AutoStrategy {

	public Strategy1A() {
		init();
	}
	
	@Override
	public void init() {
		sequences = new ArrayList<AutoSequence>();
		addSequence(new AutoSequence(new MecanumTranslate(80, 0, this), new Wait(2), new MecanumTranslate(80, 0, this)));
		// rotate 29.5, forward 146.4 inches
		// forward 115.5 inches, rotate 90, forward 72 inches, rotate 90, forward 12 inches
		// rotate 29.5, forward 146.4 inches, rotate -29.5 (back to original), forward ~4 inches
		isInitialized = true;
	}

}
