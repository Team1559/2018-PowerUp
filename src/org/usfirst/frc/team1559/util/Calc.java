package org.usfirst.frc.team1559.util;

import org.usfirst.frc.team1559.robot.Constants;

public class Calc {

	public static double distanceInTicksMecanum(double base) {
		return base * Constants.WHEEL_FUDGE_MECANUM * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
	}
	
	public static double distanceInTicksTraction(double base) {
		return base * Constants.DT_SPROCKET_RATIO * Constants.WHEEL_FUDGE_TRACTION * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
	}

	public static double angleInTicks(double base) {
		return base * 4096 * 0.019;
	}

}
