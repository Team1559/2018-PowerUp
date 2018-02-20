package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;

public class Climber {
	private static final int TIMEOUT = 0;
	private Talon winch;
	private TalonSRX lifter;
	
	public Climber() {
		winch = new Talon(Wiring.CLM_WINCH);
		lifter = new TalonSRX(Wiring.CLM_LIFT);
		lifter.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
	}
	
	public void driveUp() {
		lifter.set(ControlMode.Position, Constants.CLM_UPPER_BOUND);
	}
	
	public void driveDown() {
		lifter.set(ControlMode.Position, Constants.CLM_LOWER_BOUND);
	}
	
	public void setWinchMotor() {
		winch.set(Constants.CLM_WINCH_SPEED);
	}
}
