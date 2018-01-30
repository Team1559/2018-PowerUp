package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.auto.AutoCommand;
import org.usfirst.frc.team1559.robot.auto.AutoStrategy;

public class TimeBased extends AutoCommand {

	private int time = 0;
	private int count = 0;
	
	public TimeBased(int time, AutoStrategy parent) {
		this.time = time;
	}
	
	@Override
	protected void initialize() {
		type = AutoCommand.TYPE_MOVE;
		Robot.driveTrain.shift(true);
		Robot.driveTrain.resetEncoders();
		
		count = 0;
		
	}

	@Override
	protected void iterate() {
		Robot.driveTrain.translateAbsolute(1 * Constants.CONVERSION_FUDGE * 4096 / (2 * Math.PI * Constants.WHEEL_RADIUS_INCHES), 0);
		count++;
	}

	/*@Override
	public boolean isFinished() {
		if (count >= time * 100) {
			return true;
		}
		
		return false;
	}*/
	
}
