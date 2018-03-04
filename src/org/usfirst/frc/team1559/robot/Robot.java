/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Ingest;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_ManualShoulderRotate;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spit;
import org.usfirst.frc.team1559.robot.subsystems.Climber;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1559.robot.subsystems.Intake;
import org.usfirst.frc.team1559.robot.subsystems.Lifter;
import org.usfirst.frc.team1559.util.BNO055;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static PowerDistributionPanel pdp;

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
		setupData.updateData();

		//SmartDashboard.putNumber("Climber Current: ", pdp.getCurrent(12));
		SmartDashboard.putNumber("Motor 0 Current", driveTrain.motors[0].getOutputCurrent());
		SmartDashboard.putNumber("Lifter Pot", lifter.getPot());
		SmartDashboard.putNumber("Climber Pot", climber.getPot());
		//System.out.println("Lifter Current: "+ lifter.getMotor().getOutputCurrent());
		// SmartDashboard.putNumber("Climber Pot", climber.getPot());
		// SmartDashboard.putNumber("IMU", imu.getHeading());
	}

	@Override
	public void autonomousInit() {
		imu.zeroHeading();
		setupData.updateData();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println("POSITION IS " + setupData.getPosition());

		// TODO Change this
		routine = AutoPicker.pick(gameData, (int) setupData.getPosition());

		routine.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		intake.updateRotate();
		lifter.update();

//		SmartDashboard.putNumber("Motor 0 error: ",
//		driveTrain.motors[0].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 1 error: ",
//		driveTrain.motors[1].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 2 error: ",
//		driveTrain.motors[2].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 3 error: ",
//		driveTrain.motors[3].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 0 value: ",
//		driveTrain.motors[0].getMotorOutputVoltage());
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

		// DRIVING
		if (xbox) {
			driveTrain.drive(-oi.getDriverY(), oi.getDriverX(), -oi.getDriverZ());

			if (oi.getDriverButton(1).isPressed()) {
				driveTrain.shift();
			}

			if (oi.getDriverButton(4).isPressed()) { // LT
				intake.setActiveRotate(true);
				intake.rotateDown();
				intake.open();
			}
			if (oi.getDriverButton(4).isReleased()) {
				new WPI_Ingest().start();
			}
			if (oi.getDriverButton(5).isPressed()) { // RT
				new WPI_Spit().start();
			}
			if (oi.getDriverButton(5).isReleased()) {
				intake.stopIntake();
				intake.rotateUp();
			}
		} else { // ps4
			driveTrain.drive(-oi.getDriverY(), oi.getDriverX(), -oi.getPS4Z()); // y is pos for robot 2

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

			// MANUAL CONTROLS
			if (oi.getDriverButton(2).isPressed()) {
				new WPI_ManualShoulderRotate(!intake.isGoingDown()).start();
			}
			if (oi.getDriverButton(0).isPressed()) {
				intake.toggle();
			}
			if (oi.getDriverButton(3).isDown()) {
				intake.out();
			} else if (oi.getDriverButton(1).isDown()) {
				intake.in();
			}
			if (oi.getDriverButton(3).isReleased() || oi.getDriverButton(1).isReleased()) {
				intake.stopIntake();
			}
		}

		// LIFTING
		if (Math.abs(oi.getCopilotAxis(0)) >= 0.05 && !oi.getCocopilotButton(1).isDown()) {
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

		if (oi.getCopilotButton(0).isPressed()) {
			lifter.reset();
			System.out.println("New Pot Lower Bound: " + lifter.lowerBound);
		}

		// CLIMBING
		if (oi.getCocopilotButton(1).isDown()) {
			climber.stageOne(oi.getCopilotAxis(0));
		}

		climber.stageTwo(oi.getCocopilotButton(0).isDown());

		driveTrain.autoShift();
		intake.updateRotate();
		lifter.update();

		// SmartDashboard.putNumber("Lifter Motor Current:",
		// lifter.getMotor().getOutputCurrent());
		// SmartDashboard.putNumber("Motor 0 error: ",
		// driveTrain.motors[0].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 1 error: ",
		// driveTrain.motors[1].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 2 error: ",
		// driveTrain.motors[2].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 3 error: ",
		// driveTrain.motors[3].getClosedLoopError(0));
	}

	@Override
	public void disabledPeriodic() {

	}

	@Override
	public void testInit() {

	}

	@Override
	public void testPeriodic() {

		// System.out.println("heading " + imu.getHeading());
		// SmartDashboard.putNumber("Lifter Pot", lifter.getPot());
		// SmartDashboard.putNumber("lifter voltage",
		// lifter.getMotor().getBusVoltage());

		// System.out.println(imu.getHeading());

		// System.out.println(lifter.getPot());

		oi.update();
		if (!xbox) { // ps4
			driveTrain.drive(-oi.getDriverY(), -oi.getDriverX(), -oi.getPS4Z());

			// if (oi.getDriverButton(11).isPressed()) {
			// driveTrain.shift();
			// }

			if (oi.getDriverButton(5).isDown()) { // RT
				intake.setActiveRotate(true);
				intake.rotateDown();
			} else if (oi.getDriverButton(4).isDown()) { // LT
				intake.setActiveRotate(true);
				intake.rotateUp();
			}

			if (oi.getDriverButton(8).isDown()) { // Share
				intake.in();
			} else if (oi.getDriverButton(9).isDown()) { // Options
				intake.out();
			} else {
				intake.stopIntake();
			}

			if (oi.getDriverButton(0).isPressed()) { // []
				intake.toggle();
			}

			if (oi.getDriverButton(13).isPressed()) { // center
				climber.setWinchMotor(-0.5);
			}
		}

		// climber.setBeltMotor(oi.getDriverAxis(3)-oi.getDriverAxis(4));
		// climber.setWinchMotor(.1);

		// SmartDashboard.putNumber("Lifter Current",
		// lifter.getMotor().getOutputCurrent());

		// lifter.setMotor(oi.getDriverAxis(3)-oi.getDriverAxis(4));

		if (Math.abs(oi.getCopilotAxis(0)) >= 0.05) {
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

		if (oi.getCocopilotButton(1).isDown()) {
			//System.out.println("stage one");
			climber.stageOne(oi.getCopilotAxis(0));
		}

		climber.stageTwo(oi.getCocopilotButton(0).isDown());

		lifter.update();
		driveTrain.autoShift();
		intake.updateRotate();
	}
}
