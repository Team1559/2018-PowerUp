package org.usfirst.frc.team1559.robot.auto;

import org.usfirst.frc.team1559.robot.auto.commands.WPI_CloseClaw;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_LifterTo;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_OpenMouth;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateAbs;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateShoulder;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spintake;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spit;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_TractionMove;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_TranslateTractionForTime;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Wait;

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

		LLs.addParallel(new WPI_LifterTo(2));
		LLs.addSequential(new WPI_TractionMove(148, 0));
		LLs.addSequential(new WPI_RotateAbs(90, false));
		LLs.addSequential(new WPI_TranslateTractionForTime(0.4, 2));
		// LLs.addSequential(new WPI_TractionMove(60,0));
		LLs.addSequential(new WPI_Spit());

		RRs.addParallel(new WPI_LifterTo(2));
		RRs.addSequential(new WPI_TractionMove(148, 0));
		RRs.addSequential(new WPI_RotateAbs(-90, false));
		RRs.addSequential(new WPI_TranslateTractionForTime(0.4, 2));
		RRs.addSequential(new WPI_Spit());

		RLs.addSequential(new WPI_TractionMove(228, 0));
		RLs.addSequential(new WPI_RotateAbs(-90, false));
		RLs.addParallel(new WPI_LifterTo(2));
		RLs.addSequential(new WPI_TractionMove((180.0 + 52)/2.0, -90));
//		RLs.addSequential(new WPI_TractionMove(180 + 52, -90));
//		RLs.addSequential(new WPI_RotateAbs(-180 - 20, false)); //tune me john
//		RLs.addParallel(new WPI_LifterTo(3));
//		RLs.addSequential(new WPI_TranslateTractionForTime(0.7, 1.5));
//		RLs.addSequential(new WPI_Spit());

		LRs.addSequential(new WPI_TractionMove(228, 0));
		LRs.addSequential(new WPI_RotateAbs(90, false));
		LRs.addParallel(new WPI_LifterTo(2));
		LRs.addSequential(new WPI_TractionMove((180.0 + 52)/2.0, 90));
//		LRs.addSequential(new WPI_TractionMove(180 + 52, 90));
//		LRs.addSequential(new WPI_RotateAbs(180 + 20, false)); //tune me john
//		LRs.addParallel(new WPI_LifterTo(3));
//		LRs.addSequential(new WPI_TranslateTractionForTime(0.7, 1.5));
//		LRs.addSequential(new WPI_Spit());

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

		LLSs.addParallel(new WPI_RotateShoulder(90));
		LLSs.addParallel(new WPI_LifterTo(5));
		LLSs.addSequential(new WPI_TractionMove(262, 0)); // fwd
		LLSs.addSequential(new WPI_RotateAbs(45, true)); // turn
		LLSs.addSequential(new WPI_Spit());
		LLSs.addParallel(new WPI_RotateShoulder(90));
		LLSs.addParallel(new WPI_LifterTo(1));
		LLSs.addSequential(new WPI_TractionMove(5, 180)); // turn back
		LLSs.addParallel(new WPI_RotateShoulder(0));
		LLSs.addSequential(new WPI_TractionMove(0, 155)); //-135
		LLSs.addParallel(new WPI_OpenMouth());
		LLSs.addSequential(new WPI_TractionMove(58, 155)); //-135
		LLSs.addSequential(new WPI_CloseClaw());
		LLSs.addSequential(new WPI_Spintake(true, 0.5));
//		LLSs.addParallel(new WPI_TranslateTractionForTime(-.5, 0.29));
		LLSs.addSequential(new WPI_LifterTo(3));
		LLSs.addSequential(new WPI_Wait(.6));
		LLSs.addParallel(new WPI_Spit());
		LLSs.addSequential(new WPI_TranslateTractionForTime(0.5, 0.5));

		RRSs.addParallel(new WPI_RotateShoulder(90));
		RRSs.addParallel(new WPI_LifterTo(5));
		RRSs.addSequential(new WPI_TractionMove(262, 0)); // fwd
		RRSs.addSequential(new WPI_RotateAbs(-45, true)); // turn
		RRSs.addSequential(new WPI_Spit());
		RRSs.addParallel(new WPI_RotateShoulder(90));
		RRSs.addParallel(new WPI_LifterTo(1));
		RRSs.addSequential(new WPI_TractionMove(5, -180)); // turn back
		RRSs.addParallel(new WPI_RotateShoulder(0));
		RRSs.addSequential(new WPI_TractionMove(0, -155)); //-135
		RRSs.addParallel(new WPI_OpenMouth());
		RRSs.addSequential(new WPI_TractionMove(58, -155)); //-135
		RRSs.addSequential(new WPI_CloseClaw());
		RRSs.addSequential(new WPI_Spintake(true, 0.5));
//		RRSs.addParallel(new WPI_TranslateTractionForTime(-.5, 0.29));
		RRSs.addSequential(new WPI_LifterTo(3));
		RRSs.addSequential(new WPI_Wait(.6));
		RRSs.addParallel(new WPI_Spit());
		RRSs.addSequential(new WPI_TranslateTractionForTime(0.35, 0.5));
		//RRSs.addSequential(new WPI_TractionMove(28,-165));
		//RRSs.addSequential(new WPI_Wait(1));
		
		
		RRS.addParallel(new WPI_RotateShoulder(90));
		RRS.addParallel(new WPI_LifterTo(4));
		RRS.addSequential(new WPI_TractionMove(262, 0));
		RRS.addParallel(new WPI_RotateShoulder(90));
		RRS.addSequential(new WPI_TractionMove(18, -55));
		RRS.addSequential(new WPI_Spit());
		// untested//
		// RRS.addParallel(new WPI_RotateShoulder(true));
		// RRS.addSequential(new WPI_TractionMove(-35,-55));
		// RRS.addSequential(new WPI_CloseClaw());
		// RRS.addSequential(new WPI_LifterTo(1));

		LLS.addParallel(new WPI_RotateShoulder(90));
		LLS.addParallel(new WPI_LifterTo(4));
		LLS.addSequential(new WPI_TractionMove(262, 0));
		LLS.addParallel(new WPI_RotateShoulder(90));
		LLS.addSequential(new WPI_TractionMove(18, 55));
		LLS.addSequential(new WPI_Spit());
		// untested//
		// LLS.addParallel(new WPI_RotateShoulder(true));
		// LLS.addSequential(new WPI_TractionMove(-35,55));
		// LLS.addSequential(new WPI_CloseClaw());
		// LLS.addSequential(new WPI_LifterTo(1));

		crossLine.addSequential(new WPI_TractionMove(148, 0));
		// strategy1b = new Strategy1B();
	}

	public static CommandGroup pick(String gameData, int position, String target) {
		if(target.equalsIgnoreCase("switch")) {
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
					//System.out.println("CLS");
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
		} else if(target.equalsIgnoreCase("both")) {
			switch (gameData.toUpperCase()) {
			case "LRL":
				if (position == 0) {
					return LLs;
				} else if (position == 1) {
					return CLs;
				} else if (position == 2) {
					//return RLs;
					return RRS;
				}
				break;
			case "RLR":
				if (position == 0) {
					//return LRs;
					return LLS;
				} else if (position == 1) {
					return CRs;
				} else if (position == 2) {
					return RRs;
				}
				break;
			case "RRR":
				if (position == 0) {
					return LRs;
					//return crossLine;
				} else if (position == 1) {
					return CRs;
				} else if (position == 2) {
					return RRSs;
				}
				break;
			case "LLL":
				if (position == 0) {
					return LLSs;
				} else if (position == 1) {
					return CLs;
				} else if (position == 2) {
					return RLs;
					//return crossLine;
				}
				break;
			default:
				System.out.println("[Auto] Unknown game data was supplied, \"" + gameData
						+ "\", expected a three character combination of \"L\" and \"R\"!");
				break;
			}
			} else if(target.equalsIgnoreCase("ourside")) {
				switch (gameData.toUpperCase()) {
				case "LRL":
					if (position == 0) {
						return LLs;
					} else if (position == 1) {
						return CLs;
					} else if (position == 2) {
						return RRS;
					}
					break;
				case "RLR":
					if (position == 0) {
						return LLS;
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
		}

		return crossLine;
	}
	
}
