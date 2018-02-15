package org.usfirst.frc.team1559.robot.auto;

/**
 * A single command, or action, that the robot performs
 * 
 * @author Victor Robotics Team 1559, Software
 */
public abstract class AutoCommand {

	/**
	 * Command that will drive/move the robot
	 */
	public static final int TYPE_MOVE = 0;
	/**
	 * Command that will take in vision information and then perform something based
	 * on it
	 */
	public static final int TYPE_VISION = 1;
	/**
	 * Command that will do something when the robot is collided <br>
	 * <br>
	 * This is not finalized, and might be removed if there isn't time for collision
	 */
	public static final int TYPE_COLLISION = 2;

	/**
	 * The "parent" strategy, i.e. the strategy this command is a part of
	 */
	public AutoStrategy parent;

	/**
	 * The "type", or variant, of command (use referenced constants below)
	 * 
	 * @see #TYPE_MOVE
	 * @see #TYPE_VISION
	 * @see #TYPE_COLLISION
	 */
	public int type;
	/**
	 * Whether or not the command has finished executing
	 */
	public boolean isDone;
	/**
	 * Whether or not the command has been initialized <br>
	 * <br>
	 * This should be set to <code>true</code> at the end of {@link #init()}
	 */
	public boolean isInitialized;

	public AutoCommand() {
		
	}

	/**
	 * Initialize anything the command may need for {@link #going()}
	 */
	public void init() {
		isInitialized = true;
		initialize();
	}

	protected abstract void initialize();

	/**
	 * Called ~50 times per second, should be used as a "tick" or "update" method
	 */
	public void going() {
		assert isInitialized && parent.isInitialized;
		iterate();
	}

	/**
	 * <p>
	 * Called ~50 times per second, should be used as a "tick" or "update" method
	 * </p>
	 * 
	 * <p>
	 * This is called within {@link #going()}, so call that instead of this
	 * </p>
	 */
	protected abstract void iterate();

}
