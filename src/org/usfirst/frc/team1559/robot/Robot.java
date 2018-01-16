/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.DriveTrain.Gear;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {

	private OperatorInterface oi;
	private DriveTrain driveTrain;
	private String gameData;
	
	@Override
	public void robotInit() {
		oi = new OperatorInterface();
		driveTrain = new DriveTrain(Gear.DIFFERENTIAL);
	}

	@Override
	public void autonomousInit() {
		
		// query Game Data
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
	}

	@Override
	public void autonomousPeriodic() {

	}
	
	@Override
	public void teleopInit() {
		
	}

	@Override
	public void teleopPeriodic() {
		driveTrain.drive(oi.getDriverX(), oi.getDriverY(), oi.getDriverZ());
		if (oi.getDriverButton(1).isPressed()) {
			driveTrain.shift();
		}
	}
	
	@Override
	public void disabledInit() {
		
	}
	
	@Override
	public void disabledPeriodic() {
		
	}

	@Override
	public void testInit() {
		
	}
	
	@Override
	public void testPeriodic() {

	}
}
