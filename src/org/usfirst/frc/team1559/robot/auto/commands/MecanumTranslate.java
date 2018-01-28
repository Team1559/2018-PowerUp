package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.auto.AutoCommand;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Moves the command forward (or backward if given a negative distance)
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class MecanumTranslate extends AutoCommand {

	private double xInTicks, yInTicks;
	private final double TOLERANCE = 666;

	public MecanumTranslate(double x, double y, AutoStrategy parent) {
		this.parent = parent;
		this.xInTicks = x * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
		this.yInTicks = y * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES);
	}

	@Override
	public void initialize() {
		type = AutoCommand.TYPE_MOVE;
		Robot.driveTrain.shift(true);
		Robot.driveTrain.resetEncoders();
	}

	@Override
	public void iterate() {
		Robot.driveTrain.translate(xInTicks, yInTicks);
	}

	@Override
	public boolean isFinished() {
		double averageError = 0;
		for (int i = 0; i < 4; i++) {
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		averageError /= 4;
		SmartDashboard.putNumber("Avg Err", averageError);
		return averageError < TOLERANCE;
	}
}
