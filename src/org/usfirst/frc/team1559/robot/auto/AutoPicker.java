package org.usfirst.frc.team1559.robot.auto;

import org.usfirst.frc.team1559.robot.auto.strategies.*;

/**
 * Picks the optimal {@link AutoStrategy} for the provided game data
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class AutoPicker {

	private static Strategy1A strategy1a;
	private static Strategy1B strategy1b;
	private static Strategy2 strategy2;
	private static Strategy3 strategy3;

	public static void init() {
		strategy1a.init();
		strategy1b.init();
		strategy2.init();
		strategy3.init();
	}

	public static AutoStrategy pick(String gameData) {
		switch (gameData.toUpperCase()) {
		case "LRL":

			break;
		case "RLR":

			break;
		case "RRR":

			break;
		case "LLL":

			break;
		default:
			System.out.println("[Auto] Unknown game data was supplied, \"" + gameData
					+ "\", expected a three character combination of \"L\" and \"R\"!");
			break;
		}

		return strategy1a;
	}

}
