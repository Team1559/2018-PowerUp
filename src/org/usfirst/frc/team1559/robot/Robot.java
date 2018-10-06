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
import org.usfirst.frc.team1559.robot.auto.commands.WPI_LifterTo;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_MP;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_ManualDownOpen;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateAbs;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateShoulder;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_Spit;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_TractionMove;
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

	public static boolean xbox = false;
	public static boolean fightStick = true;
	public boolean scott = false; //for axis 4 manual toggle
	
	public final static boolean robotOne = true;

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
		
		intake.setShoulderAngle(90);

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
		SmartDashboard.putNumber("Shoulder", intake.getPot());
		SmartDashboard.putNumber("Shoulder Current", intake.getMotor().getOutputCurrent());
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
		driveTrain.setAutoConfig();
		System.out.println("POSITION IS " + setupData.getPosition());

		// TODO Fix target
		SmartDashboard.putString("target returned is:", setupData.getTarget());
		routine = AutoPicker.pick(gameData, (int) setupData.getPosition(), "scale");
		
		
		// TEMP
		routine = new CommandGroup();
		//routine.addSequential(new WPI_MP("/media/sda1/waitTest.csv", false));
		//routine.addSequential(new WPI_MP("/media/sda1/test2/test2_left_detailed.csv", false));
		routine.addSequential(new WPI_MP("/media/sda1/test.csv", false));
		//routine.addSequential(new WPI_MP("/media/sda1/MP/LLSswitch.csv", false));
		//routine.addSequential(new WPI_MP("/media/sda1/LLSS.csv", false));
//		routine.addSequential(new WPI_MP("/media/sda1/fwd40ft_test.csv", false));
		//routine.addSequential(new WPI_TractionMove(100, 0));
		//routine.addSequential(new WPI_TractionMove(0, -90));
		//routine.addSequential(new WPI_TractionSpinlate(250, 0));
		
		routine.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		//lifter.update();
		//intake.updateShoulder();
		
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
		//driveTrain.drive(0, 0, .5);
		SmartDashboard.putNumber("motor zero position",Math.abs(driveTrain.motors[0].getSensorCollection().getQuadraturePosition()));
		SmartDashboard.putNumber("motor one position",Math.abs(driveTrain.motors[1].getSensorCollection().getQuadraturePosition()));
		SmartDashboard.putNumber("motor two position",Math.abs(driveTrain.motors[2].getSensorCollection().getQuadraturePosition()));
		SmartDashboard.putNumber("motor three position",Math.abs(driveTrain.motors[3].getSensorCollection().getQuadraturePosition()));
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void teleopInit() {
		driveTrain.setTeleConfig();
		driveTrain.shift(true);
		lifter.holdPosition();		
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		oi.update();
		
		//intake.rotateSpeed(oi.getDriverAxis(3)-oi.getDriverAxis(4));
		
		SmartDashboard.putNumber("Lifter Current", lifter.getMotor().getOutputCurrent());

		// DRIVING
		if (xbox) {
			driveTrain.driveTele(-oi.getDriverY(), oi.getDriverX(), -oi.getDriverZ());

			if (oi.getDriverButton(1).isPressed()) {
				driveTrain.shift();
			}

			if (oi.getDriverButton(4).isPressed()) { // LT
				intake.setShoulderAngle(0);
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
				intake.setShoulderAngle(90);
			}
		} else { // ps4
			double xIn = OperatorInterface.squareAxis(oi.getDriverX());
			double yIn = OperatorInterface.squareAxis(oi.getDriverY());
			double zIn = oi.getPS4Z();
			if (robotOne) { //robot 1
				driveTrain.driveTele(-yIn, xIn, -zIn); // y is positive for robot 2, negative for robot 1
																					//x is neg for robot 2, pos for robot 1
			} else { //robot 2
				driveTrain.driveTele(yIn, -xIn, -zIn);
			}
			
			if (oi.getDriverButton(11).isPressed()) {
				driveTrain.shift();
				System.out.println("shifting!");
			}

			if (oi.getDriverButton(4).isPressed()) {
				intake.setShoulderAngle(0);
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
					new WPI_Spit(0.85).start(); //TODO change this to adjust spit speed
				} else {
					new WPI_Spit(0.85).start();
				}
			}
			if (oi.getDriverButton(5).isReleased()) {
				intake.stopIntake();
				new WPI_RotateShoulder(90).start();
				intake.close();
			}

			// MANUAL CONTROLS
			if (oi.getDriverButton(2).isPressed()) {
				intake.toggleRotate();
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
		if(!fightStick) {
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
			climber.stageTwo(oi.getCocopilotButton(0).isDown()); //EASY BUTTON
			
		} else { //yes fightstick!
			if (Math.abs(oi.getCopilotAxis(1)) >= 0 && !oi.getCopilotButton(8).isDown()) { //LT/L2
				lifter.driveManual(oi.getCopilotAxis(1));
			}
			if (oi.getCopilotButton(0).isPressed()) { //A/X
				lifter.setPosition(1);
			} else if (oi.getCopilotButton(3).isPressed()) { //X/SQUARE
				lifter.setPosition(2);
			} else if (oi.getCopilotButton(4).isPressed()) { //Y/TRIANGLE
				lifter.setPosition(3);
			} else if (oi.getCopilotButton(7).isPressed()) { //RB/R1
				lifter.setPosition(4);
			} else if (oi.getCopilotButton(6).isPressed()) { //LB/L1
				lifter.setPosition(5);
			}
	
			if (oi.getCopilotButton(12).isPressed()) { //PS/HOME
				lifter.reset();
				System.out.println("New Pot Lower Bound: " + lifter.lowerBound);
			}
	
			// CLIMBING
			if (oi.getCopilotButton(8).isDown()) { 
				System.out.println(oi.getCopilotAxis(1));
				climber.stageOne(oi.getCopilotAxis(1));
			} else {
				climber.stopBelting();
			}
			climber.stageTwo(oi.getCopilotButton(11).isDown()); //START/OPTIONS/MENU
		}
		
		driveTrain.autoShift();
		intake.updateShoulder();
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
		
		//System.out.println(imu.getHeading()); //for testing
		System.out.println(intake.getPot());

		oi.update();
		if (!xbox) { // ps4
			if (robotOne) { //robot 1
				//driveTrain.driveTele(-oi.getDriverY(), oi.getDriverX(), -oi.getPS4Z()); // y is positive for robot 2, negative for robot 1
																					//x is neg for robot 2, pos for robot 1
			} else { //robot 2
				//driveTrain.driveTele(oi.getDriverY(), -oi.getDriverX(), -oi.getPS4Z());
				
				//driveTrain.setAutoConfig();
				//driveTrain.driveTele(0.5,0,0);
				
//				driveTrain.getMotors()[0].set(ControlMode.Velocity, 2500);
//				driveTrain.getMotors()[1].set(ControlMode.Velocity, 1);
//				driveTrain.getMotors()[2].set(ControlMode.Velocity, 1);
//				driveTrain.getMotors()[3].set(ControlMode.Velocity, 1);
				//driveTrain.drive(0.5, 0, 0);
				//System.out.println("ControlMode: " + driveTrain.getMotors()[3].getSelectedSensorVelocity(0));
//				SmartDashboard.putNumber("Velocity", Robot.driveTrain.getMotors()[0].getSelectedSensorVelocity(0));
			}

			if (oi.getDriverButton(11).isPressed()) {
				driveTrain.shift();
			}

			if (oi.getDriverButton(5).isDown()) { // RT
				intake.setShoulderAngle(0);
			} else if (oi.getDriverButton(4).isDown()) { // LT
				intake.setShoulderAngle(90);
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

		if (Math.abs(oi.getCopilotAxis(1)) >= 0.1 && !oi.getCopilotButton(8).isDown()) {
			lifter.driveManual(oi.getCopilotAxis(1));
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

		if (oi.getCopilotButton(8).isDown()) {
			climber.stageOne(oi.getCopilotAxis(1));
		} else {
			climber.stopBelting();
		}
		
		//intake.rotateSpeed(-1);

		climber.stageTwo(oi.getCopilotButton(0).isDown());
		climber.winchDown(oi.getCopilotButton(0).isDown() && oi.getCopilotButton(1).isDown());

		lifter.update();
		//intake.updateShoulder();
	}
}
