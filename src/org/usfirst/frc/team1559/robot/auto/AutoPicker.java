package org.usfirst.frc.team1559.robot.auto;

import org.usfirst.frc.team1559.robot.auto.commands.WPI_LifterTo;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateAbs;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spit;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_TractionTranslate;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_TranslateTractionForTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Picks the optimal {@link AutoStrategy} for the provided game data
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class AutoPicker {

	private static CommandGroup leftSwitch;
	private static CommandGroup rightSwitch;
	
	private static CommandGroup leftCrossSwitch;
	private static CommandGroup rightCrossSwitch;

	private static CommandGroup crossLine;
	
	public static void init() {

		leftSwitch = new CommandGroup();
		rightSwitch = new CommandGroup();
		crossLine = new CommandGroup();
		leftCrossSwitch = new CommandGroup();
		rightCrossSwitch = new CommandGroup();

		leftSwitch.addParallel(new WPI_LifterTo(2));
		leftSwitch.addSequential(new WPI_TractionTranslate(148));
		leftSwitch.addSequential(new WPI_RotateAbs(90, false));
		leftSwitch.addSequential(new WPI_TranslateTractionForTime(0.4, 2));
		leftSwitch.addSequential(new WPI_Spit());
		
		rightSwitch.addParallel(new WPI_LifterTo(2));
		rightSwitch.addSequential(new WPI_TractionTranslate(148));
		rightSwitch.addSequential(new WPI_RotateAbs(-90, false));
		rightSwitch.addSequential(new WPI_TranslateTractionForTime(0.4, 2));
		rightSwitch.addSequential(new WPI_Spit());
		
		leftCrossSwitch.addSequential(new WPI_TractionTranslate(228));
		leftCrossSwitch.addSequential(new WPI_RotateAbs(-90, false));
		leftCrossSwitch.addParallel(new WPI_LifterTo(2));
		leftCrossSwitch.addSequential(new WPI_TractionTranslate(180));
		leftCrossSwitch.addSequential(new WPI_RotateAbs(-180, false));
		leftCrossSwitch.addParallel(new WPI_LifterTo(3));
		leftCrossSwitch.addSequential(new WPI_TranslateTractionForTime(0.299, 1));
		leftCrossSwitch.addSequential(new WPI_Spit());
		
		rightCrossSwitch.addSequential(new WPI_TractionTranslate(228));
		rightCrossSwitch.addSequential(new WPI_RotateAbs(90, false));
		rightCrossSwitch.addParallel(new WPI_LifterTo(3));
		rightCrossSwitch.addSequential(new WPI_TractionTranslate(180));
		rightCrossSwitch.addSequential(new WPI_RotateAbs(180, false));
		rightCrossSwitch.addParallel(new WPI_LifterTo(3));
		rightCrossSwitch.addSequential(new WPI_TranslateTractionForTime(0.299, 1));
		rightCrossSwitch.addSequential(new WPI_Spit());

		crossLine.addSequential(new WPI_TractionTranslate(210));
		// strategy1b = new Strategy1B();
	}

	public static CommandGroup pick(String gameData, int position) {
		switch (gameData.toUpperCase()) {
		case "LRL":
			if (position == 0) {
				return leftSwitch;
			} else if (position == 1) {
				
			} else if (position == 2) {
				return leftCrossSwitch;
			}
			break;
		case "RLR":
			if (position == 0) {
				return rightCrossSwitch;
			} else if (position == 1) {
				
			} else if (position == 2) {
				return rightSwitch;
			}
			break;
		case "RRR":
			if (position == 0) {
				return rightCrossSwitch;
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
				return leftCrossSwitch;
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
