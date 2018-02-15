package org.usfirst.frc.team1559.robot.auto;

import org.usfirst.frc.team1559.robot.auto.strategies.Strategy1A;

/**
 * Picks the optimal strategy for the provided game data
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class AutoPicker {

	private static Strategy1A strategy1a;
	// private static Strategy1B strategy1b;

	public static void init() {
		strategy1a = new Strategy1A();
		// strategy1b = new Strategy1B();
	}

	/**
	 * Gives the best auto strategy for the provided game data. Will return
	 * {@link org.usfirst.frc.team1559.robot.auto.strategies.Strategy1A Strategy1A}
	 * if an unknown game data configuration was given
	 * 
	 * @param gameData
	 *            String representation of the ownership of the switches and scale
	 * @return The optimal strategy
	 */
	public static AutoStrategy best(String gameData, double position) {
		// TODO: Pick actual strategies
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
