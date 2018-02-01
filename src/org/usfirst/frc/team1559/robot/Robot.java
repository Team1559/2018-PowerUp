/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.auto.AutoPicker;
import org.usfirst.frc.team1559.robot.auto.AutoSequence;
import org.usfirst.frc.team1559.robot.auto.commands.WPI_RotateRel;
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
	// private UDPClient udp;
	private AutoSequence autoSequence;

	private CommandGroup routine;

	@Override
	public void robotInit() {
		oi = new OperatorInterface();
		driveTrain = new DriveTrain(false);
		imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER);
		// udp = new UDPClient();
		AutoPicker.init();
		routine = new CommandGroup();
		routine.addSequential(new WPI_RotateRel(45, true));
		routine.addSequential(new WPI_RotateRel(45, true));
		routine.addSequential(new WPI_RotateRel(45, true));
		routine.addSequential(new WPI_RotateRel(45, true));
		routine.addSequential(new WPI_RotateRel(45, true));
		routine.addSequential(new WPI_RotateRel(45, true));
		routine.addSequential(new WPI_RotateRel(45, true));
		routine.addSequential(new WPI_RotateRel(45, true));
	}

	@Override
	public void robotPeriodic() {
	}

	@Override
	public void autonomousInit() {
		// query Game Data
		gameData = DriverStation.getInstance().getGameSpecificMessage();

		// AutoStrategy bestStrategy = AutoPicker.pick(gameData);
		// autoSequence = bestStrategy.sequences.get(0);

		// driveTrain.shift(true);
		// driveTrain.resetQuadEncoders();
		// autoSequence.reset();
		routine.start();
	}

	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("Error 0", driveTrain.motors[0].getClosedLoopError(0));
		SmartDashboard.putNumber("Error 1", driveTrain.motors[1].getClosedLoopError(0));
		SmartDashboard.putNumber("Error 2", driveTrain.motors[2].getClosedLoopError(0));
		SmartDashboard.putNumber("Error 3", driveTrain.motors[3].getClosedLoopError(0));
		Scheduler.getInstance().run();
		// autoSequence.execute();
		// if (autoSequence.isDone) {
		// System.out.println("The optimal auto sequence is now done!");
		// }
		// SmartDashboard.putNumber("Enc Veloci: ",
		// driveTrain.motors[0].getSensorCollection().getQuadratureVelocity());
		//// if (!foo.isFinished()) {
		//// System.out.println("foo is running");
		//// foo.iterate();
		//// } else if (foo.isFinished() && !foo2.isInitialized) {
		//// System.out.println("foo is done");
		//// foo2.initialize();
		//// } else if (!foo2.isFinished()){
		//// System.out.println("foo2 is running");
		//// foo2.iterate();
		//// }
	}

	@Override
	public void teleopInit() {
		// come on and slam
		driveTrain.shift(true);
	}

	@Override
	public void teleopPeriodic() {
		oi.update();
		driveTrain.drive(-oi.getDriverY(), oi.getDriverX(), -oi.getDriverZ());
		if (oi.getDriverButton(1).isPressed()) {
			driveTrain.shift();
		}
		System.out.println(imu.getHeading());
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
