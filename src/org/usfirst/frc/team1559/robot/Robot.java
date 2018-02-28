/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Ingest;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spit;
import org.usfirst.frc.team1559.robot.subsystems.Climber;
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
	public static Climber climber;
	public static Intake intake;

	private SetupData setupData;
	
	public boolean xbox = false;

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
		climber = new Climber();
		intake = new Intake();

		// autonomous
		routine = new CommandGroup();
		AutoPicker.init();

		setupData = new SetupData();

	}

	@Override
	public void robotPeriodic() {
		//SmartDashboard.putNumber("Lifter Pot", lifter.getPot());
		setupData.updateData();
	}

	@Override
	public void autonomousInit() {
		imu.zeroHeading();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		routine = AutoPicker.pick(gameData,
				(int) SmartDashboard.getNumber("Starting Position", setupData.getPosition()));

		// temporary: routine should be built using AutoPicker
		// routine.addSequential(new WPI_FindCube());
		// routine.addSequential(new WPI_RotateToCube(false));
		// routine.addSequential(new WPI_Spit());

		// routine.addParallel(new WPI_LifterTo(2));
		// routine.addSequential(new WPI_TractionTranslate(148));
		// routine.addSequential(new WPI_RotateAbs(90, false));
		// routine.addSequential(new WPI_Spit());
		// </temporary>

		routine.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		intake.updateRotate();
		lifter.update();
		
		SmartDashboard.putNumber("Motor 0 error: ", driveTrain.motors[0].getClosedLoopError(0));
		SmartDashboard.putNumber("Motor 1 error: ", driveTrain.motors[1].getClosedLoopError(0));
		SmartDashboard.putNumber("Motor 2 error: ", driveTrain.motors[2].getClosedLoopError(0));
		SmartDashboard.putNumber("Motor 3 error: ", driveTrain.motors[3].getClosedLoopError(0));

		SmartDashboard.putNumber("Motor 0 value: ", driveTrain.motors[0].getMotorOutputVoltage());
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void teleopInit() {
		driveTrain.shift(true);
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		oi.update();
		if (xbox) {
			driveTrain.drive(oi.getDriverY(), -oi.getDriverX(), -oi.getDriverZ());
			
			if (oi.getDriverButton(1).isPressed()) {
				driveTrain.shift();
			}
			
			if (oi.getDriverButton(4).isPressed()) {
				intake.setActiveRotate(true);
				intake.rotateDown();
				intake.open();
			}
			if (oi.getDriverButton(4).isReleased()) {
				new WPI_Ingest().start();
			}
			if (oi.getDriverButton(5).isPressed()) {
				new WPI_Spit().start();
			}
			if (oi.getDriverButton(5).isReleased()) {
				intake.stopIntake();
				intake.rotateUp();
			}		
		}
		else { //ps4
			driveTrain.drive(oi.getDriverY(), -oi.getDriverX(), -oi.getDriverAxis(2));
			
			if (oi.getDriverButton(11).isPressed()) {
				driveTrain.shift();
			}
			
			if (oi.getDriverButton(4).isPressed()) {
				intake.setActiveRotate(true);
				intake.rotateDown();
				intake.open();
			}
			if (oi.getDriverButton(4).isReleased()) {
				new WPI_Ingest().start();
			}
			if (oi.getDriverButton(5).isPressed()) {
				new WPI_Spit().start();
			}
			if (oi.getDriverButton(5).isReleased()) {
				intake.stopIntake();
				intake.rotateUp();
				intake.close();
			}
			
			if(oi.getDriverPOV() == 0) {
				intake.rotateUp();
				intake.setActiveRotate(true);
			}
			else if(oi.getDriverPOV() == 180) {
				intake.rotateDown();
				intake.setActiveRotate(true);
			}
			
		}

		if(Math.abs(oi.getCopilotAxis(0)) >= 0.05) {
			lifter.driveManual(oi.getCopilotAxis(0));
		}
		if (oi.getCopilotButton(1).isPressed()) {
			lifter.setPosition(1);
		} else if (oi.getCopilotButton(3).isPressed()) {
			lifter.setPosition(2);
		} else if (oi.getCopilotButton(5).isPressed()) {
			lifter.setPosition(3);
		} else if (oi.getCopilotButton(2).isPressed()) {
			lifter.setPosition(4);
		} else if (oi.getCopilotButton(4).isPressed()) {
			lifter.setPosition(5);
		}
			
		driveTrain.autoShift();
		intake.updateRotate();
		lifter.update();
		
		SmartDashboard.putNumber("Lifter Motor Current:", lifter.getMotor().getOutputCurrent());
		SmartDashboard.putNumber("Motor 0 error: ", driveTrain.motors[0].getClosedLoopError(0));
		SmartDashboard.putNumber("Motor 1 error: ", driveTrain.motors[1].getClosedLoopError(0));
		SmartDashboard.putNumber("Motor 2 error: ", driveTrain.motors[2].getClosedLoopError(0));
		SmartDashboard.putNumber("Motor 3 error: ", driveTrain.motors[3].getClosedLoopError(0));
	}

	@Override
	public void disabledPeriodic() {

	}

	@Override
	public void testInit() {

	}

	@Override
	public void testPeriodic() {
		System.out.println("IMU: " + imu.getHeading());
	}
}
