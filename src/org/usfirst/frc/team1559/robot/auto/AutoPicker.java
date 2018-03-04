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

	// first letter: where you start
	// second/third letter: where you cube the guy
	
	private static CommandGroup LLs;
	private static CommandGroup RRs;
	
	private static CommandGroup RLs;
	private static CommandGroup LRs;

	private static CommandGroup CLs;
	private static CommandGroup CRs;
	
	private static CommandGroup LLS;
	
	private static CommandGroup crossLine;
	
	public static void init() {

		LLs = new CommandGroup();
		RRs = new CommandGroup();
		crossLine = new CommandGroup();
		RLs = new CommandGroup();
		LRs = new CommandGroup();
		CLs = new CommandGroup();
		CRs = new CommandGroup();
		LLS = new CommandGroup();

		LLs.addParallel(new WPI_LifterTo(2));
		LLs.addSequential(new WPI_TractionTranslate(148));
		LLs.addSequential(new WPI_RotateAbs(90, false));
		LLs.addSequential(new WPI_TranslateTractionForTime(0.4, 2));
		LLs.addSequential(new WPI_Spit());
		
		RRs.addParallel(new WPI_LifterTo(2));
		RRs.addSequential(new WPI_TractionTranslate(148));
		RRs.addSequential(new WPI_RotateAbs(-90, false));
		RRs.addSequential(new WPI_TranslateTractionForTime(0.4, 2));
		RRs.addSequential(new WPI_Spit());
		
		RLs.addSequential(new WPI_TractionTranslate(228));
		RLs.addSequential(new WPI_RotateAbs(-90, false));
		RLs.addParallel(new WPI_LifterTo(2));
		RLs.addSequential(new WPI_TractionTranslate(180));
		RLs.addSequential(new WPI_RotateAbs(-180, false));
		RLs.addParallel(new WPI_LifterTo(3));
		RLs.addSequential(new WPI_TranslateTractionForTime(0.299, 1));
		RLs.addSequential(new WPI_Spit());
		
		LRs.addSequential(new WPI_TractionTranslate(228));
		LRs.addSequential(new WPI_RotateAbs(90, false));
		LRs.addParallel(new WPI_LifterTo(3));
		LRs.addSequential(new WPI_TractionTranslate(180));
		LRs.addSequential(new WPI_RotateAbs(180, false));
		LRs.addParallel(new WPI_LifterTo(3));
		LRs.addSequential(new WPI_TranslateTractionForTime(0.299, 1));
		LRs.addSequential(new WPI_Spit());
		
		CLs.addSequential(new WPI_TractionTranslate(42));
		CLs.addParallel(new WPI_LifterTo(2));
		CLs.addSequential(new WPI_RotateAbs(-55, false));
		CLs.addSequential(new WPI_TractionTranslate(64));
		CLs.addSequential(new WPI_RotateAbs(0, false));
		CLs.addSequential(new WPI_TranslateTractionForTime(0.299, 0.5));
		CLs.addSequential(new WPI_Spit());

		CRs.addSequential(new WPI_TractionTranslate(42));
		CRs.addParallel(new WPI_LifterTo(2));
		CRs.addSequential(new WPI_RotateAbs(55, false));
		CRs.addSequential(new WPI_TractionTranslate(64));
		CRs.addSequential(new WPI_RotateAbs(0, false));
		CRs.addSequential(new WPI_TranslateTractionForTime(0.299, 0.5));
		CRs.addSequential(new WPI_Spit());
		
		crossLine.addSequential(new WPI_TractionTranslate(148));
		// strategy1b = new Strategy1B();
	}

	public static CommandGroup pick(String gameData, int position) {
		switch (gameData.toUpperCase()) {
		case "LRL":
			if (position == 0) {
				return LLs;
			} else if (position == 1) {
				return CLs;
			} else if (position == 2) {
				return RLs;
			}
			break;
		case "RLR":
			if (position == 0) {
				return LRs;
			} else if (position == 1) {
				return CRs;
			} else if (position == 2) {
				return RRs;
			}
			break;
		case "RRR":
			if (position == 0) {
				return LRs;
			} else if (position == 1) {
				return CRs;
			} else if (position == 2) {
				return RRs;
			}
			break;
		case "LLL":
			if (position == 0) {
				return LLs;
			} else if (position == 1) {
				System.out.println("CLS");
				return CLs;
			} else if (position == 2) {
				return RLs;
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
