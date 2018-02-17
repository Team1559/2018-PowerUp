package org.usfirst.frc.team1559.robot;

public interface Constants {

	public final double WHEEL_RADIUS_INCHES = 3;
	public final double DT_SPROCKET_RATIO = 32.0 / 22.0;
	public final double WHEEL_FUDGE_MECANUM = 0.723;
	public final double WHEEL_FUDGE_TRACTION = 0.85;//0.6
	
	public final double CLIMB_TELESCOPE_SPEED = 0.2; //TODO: Find actual value.
	public final double CLIMB_WINCH_SPEED = 0.02; //TODO: Find actual value.
	
	public final int LIFT_TOP_LIMIT = 300; //MAX VALUE OF POT= 1023
	public final int LIFT_BOTTOM_LIMIT = 739; //MIN VALUE OF POT = 6
	public final int SCALE_TOP_LIMIT = 0;
	public final int SCALE_NEUTRAL_LIMIT = 500;
	public final int SCALE_BOTTOM_LIMIT = 0;
	public final int SWITCH_TOP_LIMIT = 0;
}
