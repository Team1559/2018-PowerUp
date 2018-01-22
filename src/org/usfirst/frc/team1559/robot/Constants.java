package org.usfirst.frc.team1559.robot;

public interface Constants {
final double pDriver = 0; //These four are the four pit function values
final double iDriver = 0;
final double dDriver = 0;
final double fDriver = 0;
final double climbSpeed = 0; //This is climber speed
final double liftHeight = 0;
final double liftGearDiameter = 0;
final double liftTime = 0;
final double gearRatio = 1/3;//This is a one to three ratio
final double liftSpeed = ((liftHeight/Math.PI*liftGearDiameter*liftTime)*gearRatio);//This is lifter speed

}
