package org.usfirst.frc.team1559.robot.auto;

import org.usfirst.frc.team1559.robot.auto.commands.WPI_LifterTo;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateAbs;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spit;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_TractionTranslate;
import org.usfirst.frc.team1559.robot.auto.strategies.Strategy1A;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Picks the optimal {@link AutoStrategy} for the provided game data
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class AutoPicker {

	private static Strategy1A strategy1a;
	
	private static CommandGroup leftSwitch;
	private static CommandGroup rightSwitch;
	private static CommandGroup crossLine;
	
	// private static Strategy1B strategy1b;

	public static void init() {
		strategy1a = new Strategy1A();
		
		leftSwitch = new CommandGroup();
		rightSwitch = new CommandGroup();
		crossLine = new CommandGroup();
		
		leftSwitch.addParallel(new WPI_LifterTo(2));
		leftSwitch.addSequential(new WPI_TractionTranslate(148));
		leftSwitch.addSequential(new WPI_RotateAbs(90, false));
		leftSwitch.addSequential(new WPI_Spit());
		
		rightSwitch.addParallel(new WPI_LifterTo(2));
		rightSwitch.addSequential(new WPI_TractionTranslate(148));
		rightSwitch.addSequential(new WPI_RotateAbs(-90, false));
		rightSwitch.addSequential(new WPI_Spit());
		
		crossLine.addParallel(new WPI_TractionTranslate(210));
		// strategy1b = new Strategy1B();
	}

	public static CommandGroup pick(String gameData, int position) {
		switch (gameData.toUpperCase()) {
		case "LRL":
			if (position == 0) {
				return leftSwitch;
			} else if (position == 1) {
				
			} else if (position == 2) {
				return crossLine;
			}
			break;
		case "RLR":
			if (position == 0) {
				return crossLine;
			} else if (position == 1) {
				
			} else if (position == 2) {
				return rightSwitch;
			}
			break;
		case "RRR":
			if (position == 0) {
				return crossLine;
			} else if (position == 1) {
				
			} else if (position == 2) {
				return rightSwitch;
			}
			break;
		case "LLL":
			if (position == 0) {
				return leftSwitch;
			} else if (position == 1) {
				
			} else if (position == 2) {
				return crossLine;
			}
			break;
		default:
			System.out.println("[Auto] Unknown game data was supplied, \"" + gameData
					+ "\", expected a three character combination of \"L\" and \"R\"!");
			break;
		}

		return crossLine;
	}

}
