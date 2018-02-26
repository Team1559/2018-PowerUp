/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Ingest;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_OpenMouth;
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
	
	private WPI_Ingest ingest;
	private WPI_Spit spit;
	private WPI_OpenMouth openMouth;

	private SetupData setupData;
	
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
		
		ingest = new WPI_Ingest();
		spit = new WPI_Spit();
		openMouth = new WPI_OpenMouth();
		
		setupData = new SetupData();
		
	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("Lifter Pot", lifter.getPot());
	}

	@Override
	public void autonomousInit() {
		setupData.updateData();
		imu.zeroHeading();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		routine = AutoPicker.pick(gameData, (int) SmartDashboard.getNumber("Starting Position", setupData.getPosition()));

		// temporary: routine should be built using AutoPicker
		// routine.addSequential(new WPI_FindCube());
		// routine.addSequential(new WPI_RotateToCube(false));
		// routine.addSequential(new WPI_Spit());
		
//		routine.addParallel(new WPI_LifterTo(2));
//		routine.addSequential(new WPI_TractionTranslate(148));
//		routine.addSequential(new WPI_RotateAbs(90, false));
//		routine.addSequential(new WPI_Spit());
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
		driveTrain.drive(oi.getDriverY(), -oi.getDriverX(), -oi.getDriverZ());
		//XBOX//
//		if (oi.getDriverButton(1).isPressed()) {
//			driveTrain.shift();
//		}
		//PS4//
		if (oi.getDriverButton(11).isPressed()) {
			driveTrain.shift();
		}

//		if (oi.getDriverButton(2).isPressed()) {
//			intake.toggle();
//		}

		//XBOX//
//		if (oi.getDriverButton(4).isPressed()) {
//			ingest.start();
//		}
//		if (oi.getDriverButton(5).isPressed()) {
//			spit.start();
//		}
//		if (oi.getDriverButton(2).isPressed()) {
//			openMouth.start();
//		}
		//CHANGES FOR PS4 CONTROLLER//
		if (oi.getDriverButton(4).isPressed()) {
			ingest.start();
		}
		if (oi.getDriverButton(5).isPressed()) {
			spit.start();
		}
		if (oi.getDriverButton(0).isPressed()) {
			openMouth.start();
		}
		if(oi.getDriverButton(6).isDown()) {
			intake.in();
		}
		else if(oi.getDriverButton(7).isDown()) {
			intake.out();
		}
		else {
			intake.stopIntake();
		}
		
//		if (oi.getDriverButton(4).isDown()) {
//			intake.setActiveRotate(true);
//		} else if (oi.getDriverButton(5).isDown()) {
//			intake.setActiveRotate(true);
//		}
//		if (oi.getDriverButton(4).isPressed()) {
//			intake.rotateUp();
//			intake.close();
//		} else if (oi.getDriverButton(5).isPressed()) {
//			intake.rotateDown();
//			intake.open();
//		}
		intake.updateRotate();

//		if (oi.getDriverButton(4).isDown()) {
//			intake.in();
//		} else if (oi.getDriverButton(5).isDown()) {
//			intake.out();
//		} else {
//			intake.stopIntake();
//		}
		double x = (oi.getDriverAxis(4)) - (oi.getDriverAxis(3));
		// System.out.println("x " + x);
		//climber.setWinchMotor(x);
		System.out.println(x);
		
		
//		if (oi.getDriverButton().isDown()) {
//			climber.setWinchMotor(.5);
//		}
//		else if (oi.getDriverButton().isDown()) {
//			climber.setWinchMotor(-0.5);
//		}

		// TODO: lifter controls should be given to copilot
//		if (Math.abs(oi.getCopilotAxis(0)) >= 0.05) {
//			System.out.println(oi.getCopilotAxis(0));
//			lifter.setManual(oi.getCopilotAxis(0));
		//} else 
		if (oi.getCopilotButton(1).isPressed()) {
			lifter.setPosition(1);// y'all put climber instead...
		} else if (oi.getCopilotButton(3).isPressed()) {
			lifter.setPosition(2);
		} else if (oi.getCopilotButton(5).isPressed()) {
			lifter.setPosition(3);
		} else if (oi.getCopilotButton(2).isPressed()) {
			lifter.setPosition(4);
		} else if (oi.getCopilotButton(4).isPressed()) {
			lifter.setPosition(5);
		} else if (oi.getCopilotButton(0).isPressed()) {
			lifter.setPosition(15);
		}

		lifter.update();
		SmartDashboard.putNumber("Lifter Motor Current:", lifter.getMotor().getOutputCurrent());
		System.out.println(lifter.getPot() + "," + Constants.LIFT_P2_TICKS);
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
