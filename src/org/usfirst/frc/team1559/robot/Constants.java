package org.usfirst.frc.team1559.robot;

public interface Constants {

	public final double WHEEL_RADIUS_INCHES = 2;
	public final double CONVERSION_FUDGE = 0.85;
	
	public final double P_DRIVER = 0; // These four are the four pit function values
	public final double I_DRIVER = 0;
	public final double D_DRIVER = 0;
	public final double F_DRIVER = 0;
	
	public final double CLIMB_SPEED = 0; // This is climber speed
	
	public final double LIFT_HEIGHT = 0;
	public final double LIFT_GEAR_DIAMETER = 0;
	public final double LIFT_TIME = 0;
	
	public final double GEAR_RATIO = 1 / 3; // This is a one to three gear ratio
	
	public final double LIFT_SPEED = ((LIFT_HEIGHT / Math.PI * LIFT_GEAR_DIAMETER * LIFT_TIME) * GEAR_RATIO); // This is
																										// lifter
																												// speed

}
