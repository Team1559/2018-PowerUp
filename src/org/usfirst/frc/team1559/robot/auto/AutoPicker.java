package org.usfirst.frc.team1559.robot.auto;

import org.usfirst.frc.team1559.robot.auto.commands.WPI_LifterTo;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_MP;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateAbs;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spit;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_TractionMove;
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

	private static CommandGroup RRSs;
	private static CommandGroup LLSs;
	private static CommandGroup RRS;
	private static CommandGroup LRS;
	private static CommandGroup RLS;
	private static CommandGroup LLS;
	private static CommandGroup LLSS;
	private static CommandGroup RRSS;
	private static CommandGroup LRSS;
	private static CommandGroup RLSS;
	private static CommandGroup LLSRs;
	private static CommandGroup RRSLs;

	private static CommandGroup crossLine;

	public static void init() {

		LLs = new CommandGroup();
		RRs = new CommandGroup();
		crossLine = new CommandGroup();
		RLs = new CommandGroup();
		LRs = new CommandGroup();
		CLs = new CommandGroup();
		CRs = new CommandGroup();
		RRSs = new CommandGroup();
		LLSs = new CommandGroup();
		LLS = new CommandGroup();
		RRS = new CommandGroup();
		LLSS = new CommandGroup();
		RRSS = new CommandGroup();
		LRSS = new CommandGroup();
		RLSS = new CommandGroup();
		LLSRs = new CommandGroup();
		RRSLs = new CommandGroup();

//		LLs.addParallel(new WPI_LifterTo(2));
//		LLs.addSequential(new WPI_TractionMove(148, 0));
//		LLs.addSequential(new WPI_RotateAbs(90, false));
//		LLs.addSequential(new WPI_TranslateTractionForTime(0.4, 2));
//		LLs.addSequential(new WPI_Spit());

//		RRs.addParallel(new WPI_LifterTo(2));
//		RRs.addSequential(new WPI_TractionMove(148, 0));
//		RRs.addSequential(new WPI_RotateAbs(-90, false));
//		RRs.addSequential(new WPI_TranslateTractionForTime(0.4, 2));
//		RRs.addSequential(new WPI_Spit());

		LLs.addSequential(new WPI_MP("/media/sda1/LLswitch.csv", false));
		RRs.addSequential(new WPI_MP("/media/sda1/LLswitch.csv", true));
		
		RLs.addSequential(new WPI_TractionMove(228, 0));
		RLs.addSequential(new WPI_RotateAbs(-90, false));
		RLs.addParallel(new WPI_LifterTo(2));
		RLs.addSequential(new WPI_TractionMove((180.0 + 52) / 2.0, -90));

		LRs.addSequential(new WPI_TractionMove(228, 0));
		LRs.addSequential(new WPI_RotateAbs(90, false));
		LRs.addParallel(new WPI_LifterTo(2));
		LRs.addSequential(new WPI_TractionMove((180.0 + 52) / 2.0, 90));

		CLs.addSequential(new WPI_TractionMove(42, 0));
		CLs.addParallel(new WPI_LifterTo(2));
		CLs.addSequential(new WPI_RotateAbs(-55, false));
		CLs.addSequential(new WPI_TractionMove(64, -55));
		CLs.addSequential(new WPI_RotateAbs(0, false));
		CLs.addSequential(new WPI_TranslateTractionForTime(0.3999, 1));
		CLs.addSequential(new WPI_Spit());

		CRs.addSequential(new WPI_TractionMove(42, 0));
		CRs.addParallel(new WPI_LifterTo(2));
		CRs.addSequential(new WPI_RotateAbs(55, false));
		CRs.addSequential(new WPI_TractionMove(64, 55));
		CRs.addSequential(new WPI_RotateAbs(0, false));
		CRs.addSequential(new WPI_TranslateTractionForTime(0.3999, 1));
		CRs.addSequential(new WPI_Spit());

		// LLSs.addParallel(new WPI_RotateShoulder(90));
		// LLSs.addParallel(new WPI_LifterTo(5));
		// LLSs.addSequential(new WPI_TractionMove(262, 0)); // fwd
		// LLSs.addSequential(new WPI_RotateAbs(45, true)); // turn
		// LLSs.addSequential(new WPI_Spit());
		// LLSs.addParallel(new WPI_RotateShoulder(90));
		// LLSs.addParallel(new WPI_LifterTo(1));
		// LLSs.addSequential(new WPI_TractionMove(5, 180)); // turn back
		// LLSs.addParallel(new WPI_RotateShoulder(0));
		// LLSs.addSequential(new WPI_TractionMove(0, 155)); //-135
		// LLSs.addParallel(new WPI_OpenMouth());
		// LLSs.addSequential(new WPI_TractionMove(58, 155)); //-135
		// LLSs.addSequential(new WPI_CloseClaw());
		// LLSs.addSequential(new WPI_Spintake(true, 0.5));
		//// LLSs.addParallel(new WPI_TranslateTractionForTime(-.5, 0.29));
		// LLSs.addSequential(new WPI_LifterTo(3));
		// LLSs.addSequential(new WPI_Wait(.6));
		// LLSs.addParallel(new WPI_Spit());
		// LLSs.addSequential(new WPI_TranslateTractionForTime(0.5, 0.5));

		// RRSs.addParallel(new WPI_RotateShoulder(90));
		// RRSs.addParallel(new WPI_LifterTo(5));
		// RRSs.addSequential(new WPI_TractionMove(262, 0)); // fwd
		// RRSs.addSequential(new WPI_RotateAbs(-45, true)); // turn
		// RRSs.addSequential(new WPI_Spit());
		// RRSs.addParallel(new WPI_RotateShoulder(90));
		// RRSs.addParallel(new WPI_LifterTo(1));
		// RRSs.addSequential(new WPI_TractionMove(5, -180)); // turn back
		// RRSs.addParallel(new WPI_RotateShoulder(0));
		// RRSs.addSequential(new WPI_TractionMove(0, -155)); //-135
		// RRSs.addParallel(new WPI_OpenMouth());
		// RRSs.addSequential(new WPI_TractionMove(58, -155)); //-135
		// RRSs.addSequential(new WPI_CloseClaw());
		// RRSs.addSequential(new WPI_Spintake(true, 0.5));
		// RRSs.addSequential(new WPI_LifterTo(3));
		// RRSs.addSequential(new WPI_Wait(.6));
		// RRSs.addParallel(new WPI_Spit());
		// RRSs.addSequential(new WPI_TranslateTractionForTime(0.35, 0.5));

		// RRS.addParallel(new WPI_RotateShoulder(90));
		// RRS.addParallel(new WPI_LifterTo(4));
		// RRS.addSequential(new WPI_TractionMove(262, 0));
		// RRS.addParallel(new WPI_RotateShoulder(90));
		// RRS.addSequential(new WPI_TractionMove(18, -55));
		// RRS.addSequential(new WPI_Spit());

		// LLS.addParallel(new WPI_RotateShoulder(90));
		// LLS.addParallel(new WPI_LifterTo(4));
		// LLS.addSequential(new WPI_TractionMove(262, 0));
		// LLS.addParallel(new WPI_RotateShoulder(90));
		// LLS.addSequential(new WPI_TractionMove(18, 55));
		// LLS.addSequential(new WPI_Spit());

		LLS.addSequential(new WPI_MP("/media/sda1/MP/LLS.csv", false));
		RRS.addSequential(new WPI_MP("/media/sda1/MP/LLS.csv", true));

		LLSs.addSequential(new WPI_MP("/media/sda1/MP/LLSswitch.csv", false));
		RRSs.addSequential(new WPI_MP("/media/sda1/MP/LLSswitch.csv", true));

		LLSS.addSequential(new WPI_MP("/media/sda1/MP/LLSS.csv", false));
		RRSS.addSequential(new WPI_MP("/media/sda1/MP/LLSS.csv", true));

		LRSS.addSequential(new WPI_MP("/media/sda1/MP/LRSS.csv", false));
		RLSS.addSequential(new WPI_MP("/media/sda1/MP/LRSS.csv", true));

		LLSRs.addSequential(new WPI_MP("/media/sda1/MP/Lcross.csv", false));
		RRSLs.addSequential(new WPI_MP("/media/sda1/MP/Lcross.csv", true));

		crossLine.addSequential(new WPI_TractionMove(148, 0));
		// strategy1b = new Strategy1B();
	}

	public static CommandGroup pick(String gameData, int position, String target) {
		char switchPosition = gameData.charAt(0);
		char scalePosition = gameData.charAt(1);
		if (target.equalsIgnoreCase("scale")) {
			if (position == 0) {
				return scalePosition == 'L' ? LLSS : LRSS;
			} else if (position == 2) {
				return scalePosition == 'L' ? RLSS : RRSS;
			} else {
				return pick(gameData, position, "switch");
			}
		} else if (target.equalsIgnoreCase("switch")) {
			if (position == 0) {
				return switchPosition == 'L' ? LLs : LRs;
			} else if (position == 1) {
				return switchPosition == 'L' ? CLs : CRs;
			} else if (position == 2) {
				return switchPosition == 'L' ? RLs : RRs;
			}
		} else if (target.equalsIgnoreCase("custom")) {
			if (position == 0) {
				if (scalePosition == 'L') {
					return switchPosition == 'L' ? LLSs : LLSS;
				} else {
					return LRSS;
				}
			} else if (position == 1) {
				return pick(gameData, position, "switch");
			} else if (position == 2) {
				if (scalePosition == 'R') {
					return switchPosition == 'R' ? RRSs : RRSS;
				} else {
					return RLSS;
				}
			}
		}
		return crossLine;
	}

}
