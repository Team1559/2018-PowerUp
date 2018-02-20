/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_TractionTranslate;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1559.robot.subsystems.Intake;
import org.usfirst.frc.team1559.robot.subsystems.Lifter;
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
	public static Lifter lifter;
	public static Intake intake;

	@Override
	public void robotInit() {

		// input
		oi = new OperatorInterface();
		imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER);
		udp = new UDPClient(); // for jetson communications
		visionData = new VisionData();

		// subsystems
		driveTrain = new DriveTrain(false);
		lifter = new Lifter();
		intake = new Intake();

		// autonomous
		routine = new CommandGroup();
		AutoPicker.init();
	}

	@Override
	public void robotPeriodic() {

	}

	@Override
	public void autonomousInit() {
		imu.zeroHeading();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		AutoPicker.pick(gameData, SmartDashboard.getNumber("Starting Position", 1));

		// temporary: routine should be built using AutoPicker
		routine.addSequential(new WPI_TractionTranslate(60));
		// </temporary>

		routine.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		intake.updateRotate();
		SmartDashboard.putNumber("Motor 0 error: ", driveTrain.motors[0].getClosedLoopError(0));
		SmartDashboard.putNumber("Motor 0 value: ", driveTrain.motors[0].getMotorOutputVoltage());
	}

	@Override
	public void teleopInit() {
		driveTrain.shift(true);
	}

	@Override
	public void teleopPeriodic() {
		oi.update();
		driveTrain.drive(oi.getDriverY(), -oi.getDriverX(), -oi.getDriverZ());
		if (oi.getDriverButton(1).isPressed()) {
			driveTrain.shift();
		}

		if (oi.getDriverButton(2).isPressed()) {
			intake.toggle();
		}

		if (oi.getDriverButton(4).isDown()) {
			intake.setActive(true);
			intake.in();
		} else if (oi.getDriverButton(5).isDown()) {
			intake.setActive(true);
			intake.out();
		} else {
			intake.stopIntake();
		}

		if (oi.getDriverButton(4).isPressed()) {
			intake.rotateUp();
		} else if (oi.getDriverButton(5).isPressed()) {
			intake.rotateDown();
		}
		intake.updateRotate();

		double x = (oi.getDriverAxis(3)) - (oi.getDriverAxis(2) * 0.25);
		lifter.setMotor(x);

		// TODO: lifter controls should be given to copilot
		if (oi.getDriverPOV() == 90) {
			lifter.toPosition(3);
		} else if (oi.getDriverPOV() == 0) {
			lifter.toPosition(2);
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
