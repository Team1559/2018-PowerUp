package org.usfirst.frc.team1559.robot.auto.motionprofiling;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;

public class FollowMotionProfile extends Command {

	MotionProfile mp;

	public FollowMotionProfile(Waypoint[] waypoints) {
		this.mp = new MotionProfile(waypoints);
	}

	@Override
	protected void initialize() {
		System.out.println("INITIALIZING " + this + " (t=" + Timer.getFPGATimestamp() + ")");
		mp.start();
	}

	@Override
	protected void execute() {
		mp.follow();
	}

	@Override
	protected boolean isFinished() {
		return false;
//		return mp.isFinished();
	}

	@Override
	protected void end() {
		System.out.println("FINISHING " + this + " (t=" + Timer.getFPGATimestamp() + ")");
	}

	@Override
	public String toString() {
		return String.format("FollowMotionProfile()");
	}
}
