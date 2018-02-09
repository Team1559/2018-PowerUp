package org.usfirst.frc.team1559.robot.autoWORK;

import org.usfirst.frc.team1559.robot.autoWORK.strategies.Strategy1;

public class Optimal {

	private static Strategy1 s1;

	public static void init() {
		s1 = new Strategy1();
	}

	/**
	 * @param gameData
	 *            The game data supplied by the driver station
	 * @return The best, or optimal, auto strategy based on the provided game data
	 */
	public static AutoStrategy get(String gameData) {
		switch (gameData) {
		case "LRL":

			break;
		case "RLR":

			break;
		case "RRR":

			break;
		case "LLL":

			break;
		default:
			System.out.println("Unknown game data was supplied, \"" + gameData
					+ "\", expected a three character combination of \"L\" and \"R\"!");
			break;
		}
		return s1;
	}

}
