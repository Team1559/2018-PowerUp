package org.usfirst.frc.team1559.robot.auto.commands;

import java.io.File;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class WPI_MP extends Command {

	private EncoderFollower left, right;

	public WPI_MP(String leftFilepath, String rightFilepath) {
		File leftFile = new File(leftFilepath);
		File rightFile = new File(rightFilepath);
		Trajectory leftTrajectory = Pathfinder.readFromCSV(leftFile);
		Trajectory rightTrajectory = Pathfinder.readFromCSV(rightFile);
		left = new EncoderFollower(leftTrajectory);
		right = new EncoderFollower(rightTrajectory);
	}

	@Override
	protected void initialize() {
		left.configureEncoder(Robot.driveTrain.getEncoder(DriveTrain.FL), 4096, 0.15);
		right.configureEncoder(Robot.driveTrain.getEncoder(DriveTrain.FR), 4096, 0.15);
		left.configurePIDVA(1.0, 0.0, 0.0, 1 / 3.05, 0);
		right.configurePIDVA(1.0, 0.0, 0.0, 1 / 3.05, 0);
	}

	@Override
	protected void execute() {
		double l = left.calculate(Robot.driveTrain.getEncoder(DriveTrain.FL));
		double r = right.calculate(Robot.driveTrain.getEncoder(DriveTrain.FR));

		double gyro_heading = Robot.imu.getHeadingRelative();    // Assuming the gyro is giving a value in degrees
		double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
		double turn = 0.8 * (-1.0/80.0) * angleDifference;

		Robot.driveTrain.motors[DriveTrain.FL].set(ControlMode.PercentOutput, l + turn);
		Robot.driveTrain.motors[DriveTrain.RL].set(ControlMode.PercentOutput, l + turn);
		Robot.driveTrain.motors[DriveTrain.FR].set(ControlMode.PercentOutput, r - turn);
		Robot.driveTrain.motors[DriveTrain.RR].set(ControlMode.PercentOutput, r - turn);
	}

	@Override
	protected boolean isFinished() {
		return left.isFinished() && right.isFinished();
	}

	@Override
	protected void end() {
	}
}
