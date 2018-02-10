/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_MecanumTranslate;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateAbs;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Wait;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1559.util.BNO055;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static OperatorInterface oi;
	public static DriveTrain driveTrain;
	public static BNO055 imu;
	private String gameData;
	private CommandGroup routine;
	public static UDPClient udp;
	public static VisionData visionData;

	@Override
	public void robotInit() {
		oi = new OperatorInterface();
		driveTrain = new DriveTrain(false);
		imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER);
		AutoPicker.init();
		routine = new CommandGroup();
		udp = new UDPClient();
		visionData = new VisionData();
	}

	@Override
	public void robotPeriodic() {

	}

	@Override
	public void autonomousInit() {
		imu.zeroHeading();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		AutoPicker.pick(gameData, SmartDashboard.getNumber("Starting Position", 1));

		Robot.driveTrain.resetQuadEncoders();

		routine = new CommandGroup();
		// double distance = 43;

		byte r = 4;

		// Demo Sequences
		switch (r) {
		case 0:
			// routine.addSequential(new WPI_MecanumTranslate(127.5, 0));
			// routine.addSequential(new WPI_MecanumTranslate(127.5, 16));
			break;
		case 1:
//			routine.addSequential(new WPI_MecanumTranslate(127.5, 0));
//			routine.addSequential(new WPI_MecanumTranslate(127.5, -16));
			break;
		case 2:
//			routine.addSequential(new WPI_MecanumTranslate(146.4, 0));
//			routine.addSequential(new WPI_RotateAbs(29.5, true));
			break;
		case 3:
//			routine.addSequential(new WPI_MecanumTranslate(134.9, 0));
//			routine.addSequential(new WPI_RotateAbs(19, true));
		case 4:
			routine.addSequential(new WPI_MecanumTranslate(80, 0, 0));
			routine.addSequential(new WPI_RotateAbs(90, true));
			routine.addSequential(new WPI_MecanumTranslate(20, 0, 0));
			routine.addSequential(new WPI_RotateAbs(0, true));
			routine.addSequential(new WPI_MecanumTranslate(80, 0, -90));
			break;
		default:
			break;
		}

		// routine.addSequential(new WPI_MecanumTranslate(distance, 0));
		// System.out.println("Done with translate!");
		// routine.addSequential(new WPI_RotateRel(90, true));
		/*
		 * routine.addSequential(new WPI_Wait(1.5)); routine.addSequential(new
		 * WPI_RotateRel(90, true)); routine.addSequential(new WPI_Wait(1.5));
		 * routine.addSequential(new WPI_MecanumTranslate(distance / 2, 0));
		 * routine.addSequential(new WPI_Wait(1.5)); routine.addSequential(new
		 * WPI_RotateRel(90, true)); routine.addSequential(new WPI_Wait(1.5));
		 * routine.addSequential(new WPI_MecanumTranslate(distance, 0));
		 * routine.addSequential(new WPI_Wait(1.5)); routine.addSequential(new
		 * WPI_RotateRel(90, true)); routine.addSequential(new WPI_Wait(1.5));
		 * routine.addSequential(new WPI_MecanumTranslate(distance / 2, 0));
		 * routine.addSequential(new WPI_Wait(1.5)); routine.addSequential(new
		 * WPI_RotateRel(90, true));
		 */
		routine.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Motor 0 error: ", driveTrain.motors[0].getClosedLoopError(0));
	}

	@Override
	public void teleopInit() {
		driveTrain.shift(true);
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
