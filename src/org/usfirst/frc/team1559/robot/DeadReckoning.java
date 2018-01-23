package org.usfirst.frc.team1559.robot;

/**
 * <p>
 * Various strategies for initial autonomous movement, all going to the switch
 * from any of the three starting positions. The distances used are only rough
 * estimates and are subject to change.
 * </p>
 * <p>
 * Still a work in progress, as of January 23
 * </p>
 * 
 * @author Evan Gartley (Victor Robotics Team 1559, Software)
 * @see DriveTrain
 */
public interface DeadReckoning {

	// 1A (1)
	// I) 111 inches forward, 72 inches to the left
	// II) turn 29.5 degrees, forward 146.4 inches (strafe)
	// III) 115.5 inches forward, turn 90 degrees, forward 72 inches, turn another
	// 90 degrees, forward 12 inches
	// IIII) turn 29.5 degrees, forward 146.4 inches, turn -29.5 degrees (back),
	// foward a few inches

	// 1B (2)
	// I) 111 inches forward, 44 inches to the right
	// II) turn 19.0 degrees, forward 134.9 inches (strafe)
	// III) 115.5 inches forwards, turn 90 degrees, forward 44 inches, turn another
	// 90 degrees, forward 12 inches
	// IIII) turn 19.0 degrees, forward 134.9 inches, turn -19.0 degrees (back),
	// forward a few inches

	// 2 (3)
	// 127.5 inches forward, 16 inches to the left

	// 3 (4)
	// 127.5 inches forward, 16 inches to the right

	/**
	 * TODO description
	 */
	static final byte STRATEGY_1A = 0;
	/**
	 * TODO description
	 */
	static final byte STRATEGY_1B = 1;
	/**
	 * TODO description
	 */
	static final byte STRATEGY_2 = 2;
	/**
	 * TODO description
	 */
	static final byte STRATEGY_3 = 3;

	/**
	 * Runs the given strategy
	 * 
	 * @param strategy
	 *            The number of the strategy to run (use the constants linked below)
	 * @see #STRATEGY_1A
	 * @see #STRATEGY_1B
	 * @see #STRATEGY_2
	 * @see #STRATEGY_3
	 */
	public static void go(byte strategy) {
		go(strategy, (byte) 0);
	}

	/**
	 * Runs the given strategy and sequence
	 * 
	 * @param strategy
	 *            The number of the strategy to run (use the constants linked below)
	 * @param sequence
	 *            The number of the sequence to run (0, 1, 2, or 3)
	 * @see #STRATEGY_1A
	 * @see #STRATEGY_1B
	 * @see #STRATEGY_2
	 * @see #STRATEGY_3
	 */
	public static void go(byte strategy, byte sequence) {
		switch (strategy) {
		case STRATEGY_1A:
			firstA(sequence);
			break;
		case STRATEGY_1B:
			firstB(sequence);
			break;
		case STRATEGY_2:
			// TODO: implement strategy 2
			break;
		case STRATEGY_3:
			// TODO: implement strategy 3
			break;
		default:
			System.out.println(
					"[DeadReckoning] Unknown strategy number was passed, \"" + strategy + "\", expected 0, 1, 2 or 3");
			break;
		}
	}

	static void firstA(byte sequence) {
		// TODO: implement strategy 1A
	}

	static void firstB(byte sequence) {
		// TODO: implement strategy 1B
	}

}
