/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private OperatorInterface oi;
	public static DriveTrain driveTrain;
	private String gameData;
	private UDPClient udp;
	private AutoStrategy bestStrategy;

	private int temp;

	@Override
	public void robotInit() {
		oi = new OperatorInterface();
		driveTrain = new DriveTrain(false);
		udp = new UDPClient();
		AutoPicker.init();
	}

	@Override
	public void robotPeriodic() {
	}

	@Override
	public void autonomousInit() {
		// query Game Data
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		bestStrategy = AutoPicker.pick(gameData);
	}

	@Override
	public void autonomousPeriodic() {
		bestStrategy.sequences.get(0).execute();
	}

	@Override
	public void teleopInit() {
		// come on and slam
	}

	@Override
	public void teleopPeriodic() {
		oi.update();
		driveTrain.drive(-oi.getDriverY(), oi.getDriverX(), -oi.getDriverZ());
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
