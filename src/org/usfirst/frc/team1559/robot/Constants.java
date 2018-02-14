package org.usfirst.frc.team1559.robot;

public interface Constants {

	public final double WHEEL_RADIUS_INCHES = 3;
	public final double DT_SPROCKET_RATIO = 32.0 / 22.0;
	public final double WHEEL_FUDGE = 0.723;
	
	public final double P_DRIVER = 0; // These four are the four pit function values
	public final double I_DRIVER = 0;
	public final double D_DRIVER = 0;
	public final double F_DRIVER = 0;
	
	public final double CLIMB_TELESCOPE_SPEED = 0.2; //TODO: Find actual value.
	public final double CLIMB_WINCH_SPEED = 0.02; //TODO: Find actual value.
	
	public final int LIFT_TOP_LIMIT = 1023; //MAX VALUE OF POT= 1023
	public final int LIFT_BOTTOM_LIMIT = 6; //MIN VALUE OF POT = 6
	public final int SCALE_TOP_LIMIT = 0;
	public final int SCALE_NEUTRAL_LIMIT = 0;
	public final int SCALE_BOTTOM_LIMIT = 0;
	public final int SWITCH_TOP_LIMIT = 0;
	
	public final double LIFT_HEIGHT = 0;
	public final double LIFT_GEAR_DIAMETER = 0;
	public final double LIFT_TIME = 0;
	public final double GEAR_RATIO = 1 / 3; // This is a one to three gear ratio
	public final double LIFT_SPEED = ((LIFT_HEIGHT / Math.PI * LIFT_GEAR_DIAMETER * LIFT_TIME) * GEAR_RATIO); // This is lifter speed

}
