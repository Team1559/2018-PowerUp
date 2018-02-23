package org.usfirst.frc.team1559.robot.auto.motionprofiling;

import org.usfirst.frc.team1559.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class MotionProfile {

	// most of these should be in Constants
	private static final double DT = 0.02;
	private static final double MAX_VELOCITY = 11; // m/s
	private static final double MAX_ACCELERATION = 2; // m/s^2
	private static final double MAX_JERK = 60.0; // m/s^3
	private static final int TICKS_PER_REVOLUTION = 4096;
	private static final double WHEEL_DIAMETER = 0.15875;
	private static final double WHEELBASE_WIDTH = 0.2794;

	private Waypoint[] waypoints;
	private Trajectory.Config config;
	private Trajectory trajectory;
	private TankModifier modifier;
	private EncoderFollower left, right;

	public MotionProfile(Waypoint[] waypoints) {
		this.waypoints = waypoints;
		this.config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, DT,
				MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
		this.trajectory = Pathfinder.generate(waypoints, config);
		this.modifier = new TankModifier(trajectory).modify(WHEELBASE_WIDTH);
		this.left = new EncoderFollower(modifier.getLeftTrajectory());
		this.right = new EncoderFollower(modifier.getRightTrajectory());
	}

	// run when ready to follow path
	public void start() {
		left.configurePIDVA(1.0, 0.0, 0.0, 1 / MAX_VELOCITY, 0.0);
		right.configurePIDVA(1.0, 0.0, 0.0, 1 / MAX_VELOCITY, 0.0);
		left.configureEncoder(Robot.driveTrain.getAvgLeftEncoderPosition(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);
		right.configureEncoder(Robot.driveTrain.getAvgRightEncoderPosition(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);
	}

	// run in control loop
	public void follow() {
		double l = left.calculate(Robot.driveTrain.getAvgLeftEncoderPosition());
		double r = right.calculate(Robot.driveTrain.getAvgLeftEncoderPosition());

		double heading = Robot.imu.getHeadingRelative();
		double desiredHeading = Pathfinder.r2d(left.getHeading());
		double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - heading);
		// "0.8 * (-1.0/80.0) * angleDifference comes from Team 254's TrajectoryLib, and
		// we found it works very well in most cases."
		double turn = 0.8 * (-1.0 / 80.0) * angleDifference;

		SmartDashboard.putNumber("Left", -l + turn);
		SmartDashboard.putNumber("Right", r + turn);
		
		Robot.driveTrain.setLeftMotors(ControlMode.PercentOutput, -l + turn);
		Robot.driveTrain.setRightMotors(ControlMode.PercentOutput, r - turn);
	}

	public boolean isFinished() {
		boolean leftFinished = left.isFinished();
		boolean rightFinished = right.isFinished();
		if (leftFinished ^ rightFinished) {
			System.err.println("Left trajectory didn't finish at the same time as right trajectory???");
		}
		return leftFinished && rightFinished;
	}
	
	public Waypoint[] getWaypoints() {
		return waypoints;
	}
}
