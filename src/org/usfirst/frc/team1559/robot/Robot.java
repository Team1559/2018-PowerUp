/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private OperatorInterface oi;
	private DriveTrain driveTrain;
	private String gameData;
	UDPClient udp;

	@Override
	public void robotInit() {
		oi = new OperatorInterface();
		driveTrain = new DriveTrain(false);
		udp = new UDPClient();
	}

	@Override
	public void robotPeriodic() {
	}

	private int temp;
	
	@Override
	public void autonomousInit() {
		// query Game Data
		temp = driveTrain.getAverageEncoderValue();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	}

	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("Avg Enc Pos", driveTrain.getAverageEncoderValue() - temp);
		if (driveTrain.getAverageEncoderValue() - temp <= 4 * 4096) {
			driveTrain.drive(0.35, 0, 0);
		} else {
			driveTrain.drive(0, 0, 0);
		}
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
