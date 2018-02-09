/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_MecanumTranslate;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateRel;
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

	@Override
	public void robotInit() {
		oi = new OperatorInterface();
		driveTrain = new DriveTrain(false);
		imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER);
		AutoPicker.init();
		routine = new CommandGroup();
	}

	@Override
	public void robotPeriodic() {

	}

	@Override
	public void autonomousInit() {
		double pos = SmartDashboard.getNumber("Starting Position", 1);
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		AutoPicker.pick(gameData, pos);

		routine = new CommandGroup();
		int distance = 45;
		routine.addSequential(new WPI_MecanumTranslate(distance, 0));
		routine.addSequential(new WPI_Wait(1));
		routine.addSequential(new WPI_RotateRel(90, true));
		routine.addSequential(new WPI_Wait(1));
		routine.addSequential(new WPI_MecanumTranslate(distance / 2, 0));
		routine.addSequential(new WPI_Wait(1));
		routine.addSequential(new WPI_RotateRel(90, true));
		routine.addSequential(new WPI_Wait(1));
		routine.addSequential(new WPI_MecanumTranslate(distance, 0));
		routine.addSequential(new WPI_Wait(1));
		routine.addSequential(new WPI_RotateRel(90, true));
		routine.addSequential(new WPI_Wait(1));
		routine.addSequential(new WPI_MecanumTranslate(distance / 2, 0));
		routine.addSequential(new WPI_Wait(1));
		routine.addSequential(new WPI_RotateRel(90, true));

		routine.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
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
		if (oi.getCopilotButton(Wiring.BTN_LIFT_SWITCH));
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
