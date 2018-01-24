package org.usfirst.frc.team1559.robot.auto;

/**
 * A single command, or action, that the robot performs
 * 
 * @author Evan Gartley (Victor Robotics Team 1559, Software)
 */
public abstract class AutoCommand {

	public static final int TYPE_MOVE = 0;
	public static final int TYPE_VISION = 1;

	public int type;
	public boolean isDone;

	public abstract void init(int type);

	public abstract void going();

	public static void moveForward(int inches) {

	}

	public static void moveBackwards(int inches) {

	}

	public static void moveLeftwards(int inches) {

	}

	public static void moveRightwards(int inches) {

	}
	
	public static void turn(int degrees) {
		
	}
	
	public static void strafe(int degrees, int inches) {
		turn(degrees);
		// etc
	}

}
