/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.AutoSequence;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static OperatorInterface oi;
	public static DriveTrain driveTrain;
	private String gameData;
	private UDPClient udp;
	private AutoSequence autoSequence;

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
		AutoStrategy bestStrategy = AutoPicker.pick(gameData);
		autoSequence = bestStrategy.sequences.get(0);
		driveTrain.shift(true);
		autoSequence.reset();
	}

	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("Error 0", driveTrain.motors[0].getClosedLoopError(0));
		SmartDashboard.putNumber("Error 1", driveTrain.motors[1].getClosedLoopError(0));
		SmartDashboard.putNumber("Error 2", driveTrain.motors[2].getClosedLoopError(0));
		SmartDashboard.putNumber("Error 3", driveTrain.motors[3].getClosedLoopError(0));
		autoSequence.execute();
		SmartDashboard.putNumber("Enc Veloci: ", driveTrain.motors[0].getSensorCollection().getQuadratureVelocity());
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
