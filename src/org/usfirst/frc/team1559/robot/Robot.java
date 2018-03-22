/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Ingest;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_IngestNoSpin;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_ManualDownOpen;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateShoulder;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spit;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spit2;
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
	public boolean scott = false; //for axis 4 manual toggle
	
	public final static boolean robotOne = false;

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
		
		//TODO remove this
		//udp.send("c");

		//SmartDashboard.putNumber("Motor 0 Current", driveTrain.motors[0].getOutputCurrent());
		
		//TODO use these to test
		SmartDashboard.putNumber("Lifter Pot", lifter.getPot());
		//SmartDashboard.putNumber("Lifter Current", lifter.getMotor().getOutputCurrent());
		//SmartDashboard.putNumber("Lifter Percent", lifter.getMotor().getMotorOutputPercent());
		SmartDashboard.putNumber("Climber Pot", climber.getPot());

		SmartDashboard.putNumber("IMU", imu.getHeadingRelative());

//		SmartDashboard.putNumber("Motor 0 CL Error", driveTrain.getMotors()[0].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 1 CL Error", driveTrain.getMotors()[1].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 2 CL Error", driveTrain.getMotors()[2].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 3 CL Error", driveTrain.getMotors()[3].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 0 CL Target", driveTrain.getMotors()[0].getClosedLoopTarget(0));
//		SmartDashboard.putNumber("Motor 1 CL Target", driveTrain.getMotors()[1].getClosedLoopTarget(0));
//		List<Integer> errors = MathUtils.map((x) -> Math.abs(((WPI_TalonSRX) x).getClosedLoopError(0)),
//				Robot.driveTrain.motors);
//		SmartDashboard.putNumber("Average Motor CL Error", MathUtils.average(errors));
	}

	@Override
	public void autonomousInit() {
		imu.zeroHeading();
		setupData.updateData();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println("POSITION IS " + setupData.getPosition());

		// TODO Fix target
		SmartDashboard.putString("target returned is:", setupData.getTarget());
		routine = AutoPicker.pick(gameData, (int) setupData.getPosition(), "both");
		
		//routine = new CommandGroup();
		
		routine.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		intake.updateRotate();
		lifter.update();
		
		//udp.send("c");

		//SmartDashboard.putNumber("rpm", driveTrain.getAverageRPM());
		
		//SmartDashboard.putNumber("Motor 0 error: ",driveTrain.motors[0].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 1 error: ",
		// driveTrain.motors[1].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 2 error: ",
		// driveTrain.motors[2].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 3 error: ",
		// driveTrain.motors[3].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 0 value: ",
		// driveTrain.motors[0].getMotorOutputVoltage());
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void teleopInit() {
		driveTrain.shift(true);
		lifter.holdPosition();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		oi.update();
		
		SmartDashboard.putNumber("Lifter Current", lifter.getMotor().getOutputCurrent());

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
			if (robotOne) { //robot 1
				driveTrain.drive(-oi.getDriverY(), oi.getDriverX(), -oi.getPS4Z()); // y is positive for robot 2, negative for robot 1
																					//x is neg for robot 2, pos for robot 1
			} else { //robot 2
				driveTrain.drive(oi.getDriverY(), -oi.getDriverX(), -oi.getPS4Z());
			}
			
			if (oi.getDriverButton(11).isPressed()) {
				driveTrain.shift();
				System.out.println("shifting!");
			}

			if (oi.getDriverButton(4).isPressed()) {
				intake.setActiveRotate(true);
				intake.rotateDown();
				intake.open();
			}
			if (oi.getDriverButton(4).isReleased()) {
				new WPI_Ingest().start();
			}
			if (oi.getDriverAxis(4) > 0.5 && !scott) {
				scott = true;
				new WPI_ManualDownOpen().start();
			} else if (oi.getDriverAxis(4) < 0.5 && scott){
				new WPI_IngestNoSpin().start();
				scott = false;
			}
			if (oi.getDriverButton(5).isPressed()) {
				if(lifter.isAtPosition(5)) {
					new WPI_Spit().start();
				} else {
					new WPI_Spit().start();
				}
			}
			if (oi.getDriverButton(5).isReleased()) {
				intake.stopIntake();
				new WPI_RotateShoulder(true).start();
				intake.close();
			}

			// MANUAL CONTROLS
			if (oi.getDriverButton(2).isPressed()) {
				intake.toggleRotate();
			}
			if (oi.getDriverButton(2).isDown()) {
				intake.setActiveRotate(true);
			} else {
				intake.setActiveRotate(false);
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
		} else if (oi.getCopilotButton(1).isDown() && oi.getCopilotButton(3).isDown()) { //redundant failsafe, replace climb button with pressing 1 and 2 on lifter
			climber.stageOne(oi.getCopilotAxis(0));
		}
		else {
			climber.stopBelting();
		}

		//winch if easy button or if 1 and 4 are held
		climber.stageTwo(oi.getCocopilotButton(0).isDown() || (oi.getCopilotButton(1).isDown() && oi.getCopilotButton(2).isDown()));
		
		driveTrain.autoShift();
		intake.updateRotate();
		lifter.update();
	}

	@Override
	public void disabledPeriodic() {

	}

	@Override
	public void testInit() {

	}

	@Override
	public void testPeriodic() {
		
		System.out.println(imu.getHeading()); //for testing

		oi.update();
		if (!xbox) { // ps4
			if (robotOne) { //robot 1
				driveTrain.drive(-oi.getDriverY(), oi.getDriverX(), -oi.getPS4Z()); // y is positive for robot 2, negative for robot 1
																					//x is neg for robot 2, pos for robot 1
			} else { //robot 2
				driveTrain.drive(oi.getDriverY(), -oi.getDriverX(), -oi.getPS4Z());
			}

			if (oi.getDriverButton(11).isPressed()) {
				driveTrain.shift();
			}

			if (oi.getDriverButton(5).isDown()) { // RT
				intake.setActiveRotate(true);
				intake.rotateDown();
			} else if (oi.getDriverButton(4).isDown()) { // LT
				intake.setActiveRotate(true);
				intake.rotateUp();
			}
			
			if (oi.getDriverButton(2).isPressed()) { // O
				intake.toggleRotate();
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

			if (oi.getDriverButton(13).isDown()) { // center
				climber.setWinchMotor(-0.5);
			}
		}

		if (Math.abs(oi.getCopilotAxis(0)) >= 0.1) {
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
			climber.stageOne(oi.getCopilotAxis(0));
		} else {
			climber.stopBelting();
		}

		climber.stageTwo(oi.getCocopilotButton(0).isDown());
		climber.winchDown(oi.getCocopilotButton(0).isDown() && oi.getCocopilotButton(1).isDown());

		lifter.update();
		intake.updateRotate();
	}
}
