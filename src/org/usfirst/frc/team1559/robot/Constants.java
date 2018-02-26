package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.util.MathUtils;

public interface Constants {

	public final double WHEEL_RADIUS_INCHES_MECANUM = 3;
	public final double WHEEL_RADIUS_INCHES_TRACTION = 3.125;
	public final double DT_SPROCKET_RATIO = 32.0 / 22.0;
	public final double WHEEL_FUDGE_MECANUM = 0.723;
	public final double WHEEL_FUDGE_TRACTION = 1;//0.6
	
	public final double CLM_WINCH_SPEED = 0.75; //TODO: Find actual value.
	public final int CLM_UPPER_BOUND = 0; 
	public final int CLM_LOWER_BOUND = 0;
	
	public final int LIFT_DIFFERENCE = 510; //difference between up and down in ticks
	public final int LIFT_LOWER_BOUND = 255; //MIN VALUE OF POT = 6
	public final int LIFT_UPPER_BOUND = LIFT_LOWER_BOUND + LIFT_DIFFERENCE; //MAX VALUE OF POT= 1023
	
	public final double LIFT_P5_INCHES = 85.9;
	public final double LIFT_P4_INCHES = 73.9;
	public final double LIFT_P3_INCHES = 61.9;
	public final double LIFT_P2_INCHES = 29.5;
	public final double LIFT_P1_5_INCHES = 18.75;
	public final double LIFT_P1_INCHES = 8.75;
	
	public final double LIFT_P5_TICKS = MathUtils.map(LIFT_P5_INCHES, 8.75, 80.5, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	public final double LIFT_P4_TICKS = MathUtils.map(LIFT_P4_INCHES, 8.75, 80.5, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	public final double LIFT_P3_TICKS = MathUtils.map(LIFT_P3_INCHES, 8.75, 80.5, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	public final double LIFT_P2_TICKS = MathUtils.map(LIFT_P2_INCHES, 8.75, 80.5, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	public final double LIFT_P1_TICKS = MathUtils.map(LIFT_P1_INCHES, 8.75, 80.5, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
	public final double LIFT_P1_5_TICKS = MathUtils.map(LIFT_P1_5_INCHES, 8.75, 80.5, LIFT_LOWER_BOUND, LIFT_UPPER_BOUND);
}
