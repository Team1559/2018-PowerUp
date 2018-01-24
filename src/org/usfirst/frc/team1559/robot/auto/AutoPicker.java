package org.usfirst.frc.team1559.robot.auto;

public class AutoPicker {

	private static AutoStrategy strategy1;
	private static AutoStrategy strategy2;

	public static void init() {
		strategy1 = new AutoStrategy(0);
		strategy1.addSequence(new AutoSequence(new AutoCommand[] {}));
		strategy1.addSequence(new AutoSequence(new AutoCommand[] {}));

		strategy2 = new AutoStrategy(1);
		strategy2.addSequence(new AutoSequence(new AutoCommand[] {}));
		strategy2.addSequence(new AutoSequence(new AutoCommand[] {}));
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
					+ "\", expected a three-character combination of \"L\" and \"R\"!");
			break;
		}

		return strategy1;
	}

}
