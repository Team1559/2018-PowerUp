package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;

public class Climber {
	private static final int TIMEOUT = 0;
	private Talon winch;
	private WPI_TalonSRX lifter;
	
	public Climber() {
		winch = new Talon(Wiring.CLM_WINCH);
		lifter = new WPI_TalonSRX(Wiring.CLM_LIFT);
		lifter.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
	}
	
	public double getPot() {
		return lifter.getSelectedSensorPosition(0);
	}
	
	public void driveUp() {
		lifter.set(ControlMode.Position, Constants.CLM_UPPER_BOUND);
	}
	
	public void driveDown() {
		lifter.set(ControlMode.Position, Constants.CLM_LOWER_BOUND);
	}
	
	public void setWinchMotor(double x) {
		winch.set(x);
	}
	
	public void setMotor(double x) {
		lifter.set(ControlMode.PercentOutput, x);
	}
	
	public WPI_TalonSRX getMotor() {
		return lifter;
	}
	
	public Talon getWinchMotor() {
		return winch;
	}
	
	public void stopMotor() {
		lifter.set(ControlMode.PercentOutput, 0);
	}
	
	public void stopWinchMotor() {
		winch.set(0);
	}
}
