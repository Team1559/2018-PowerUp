package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.util.MathUtils;

public interface Constants {

	public final double WHEEL_RADIUS_INCHES = 3;
	public final double DT_SPROCKET_RATIO = 32.0 / 22.0;
	public final double WHEEL_FUDGE_MECANUM = 0.723;
	public final double WHEEL_FUDGE_TRACTION = 0.85;//0.6
	
	public final double CLIMB_TELESCOPE_SPEED = 0.2; //TODO: Find actual value.
	public final double CLIMB_WINCH_SPEED = 0.02; //TODO: Find actual value.
	
	public final int LIFT_UPPER_BOUND = 300; //MAX VALUE OF POT= 1023
	public final int LIFT_LOWER_BOUND = 739; //MIN VALUE OF POT = 6
	
	public final double LIFT_P5_INCHES = 85.9;
	public final double LIFT_P4_INCHES = 73.9;
	public final double LIFT_P3_INCHES = 61.9;
	public final double LIFT_P2_INCHES = 29.5;
	public final double LIFT_P1_INCHES = 8.75;
	
	public final double LIFT_P5_TICKS = MathUtils.map(LIFT_P5_INCHES, 8.75, 85.9, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	public final double LIFT_P4_TICKS = MathUtils.map(LIFT_P4_INCHES, 8.75, 85.9, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	public final double LIFT_P3_TICKS = MathUtils.map(LIFT_P3_INCHES, 8.75, 85.9, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	public final double LIFT_P2_TICKS = MathUtils.map(LIFT_P2_INCHES, 8.75, 85.9, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	public final double LIFT_P1_TICKS = MathUtils.map(LIFT_P1_INCHES, 8.75, 85.9, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	
}
