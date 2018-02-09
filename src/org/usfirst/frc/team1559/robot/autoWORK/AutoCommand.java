package org.usfirst.frc.team1559.robot.autoWORK;

public abstract class AutoCommand {

	public boolean isFinished;

	/**
	 * Anything that may need to be run <i>once</i> before {@link #go()} starts to
	 * be called
	 */
	public abstract void init();

	/**
	 * <p>
	 * "Go" through the command, and do whatever needs to be done to complete it
	 * </p>
	 * <p>
	 * {@link #isFinished} should be to set to <code>true</code> when done
	 * </p>
	 */
	public abstract void go();

}
