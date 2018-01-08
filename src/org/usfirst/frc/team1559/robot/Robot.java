/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private String gameData;
	
	@Override
	public void robotInit() {

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
