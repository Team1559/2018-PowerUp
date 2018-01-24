package org.usfirst.frc.team1559.robot;

public interface Constants {
	
	final double P_DRIVER = 0; // These four are the four pit function values
	final double I_DRIVER = 0;
	final double D_DRIVER = 0;
	final double F_DRIVER = 0;
	final double CLIMB_SPEED = 0; // This is climber speed
	final double LIFT_HEIGHT = 0;
	final double LIFT_GEAR_DIAMETER = 0;
	final double LIFT_TIME = 0;
	final double GEAR_RATIO = 1 / 3; // This is a one to three ratio
	final double LIFT_SPEED = ((liftHeight / Math.PI * liftGearDiameter * liftTime) * gearRatio); // This is lifter speed

}
