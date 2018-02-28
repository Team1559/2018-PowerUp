package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Talon;

public class Climber {
	private static final int TIMEOUT = 0;
	private Talon winch;
	private WPI_TalonSRX belt;
	private double displacement;
	
	public Climber() {
		winch = new Talon(Wiring.CLM_WINCH);
		belt = new WPI_TalonSRX(Wiring.CLM_BELT);
		belt.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		displacement = 0;
	}
	
	public double getPot() {
		return belt.getSelectedSensorPosition(0);
	}

	public void stageOne() {
		belt.set(ControlMode.Position, getPot() + displacement); // TODO: lol robots don't quit
	}
	
	public void stageTwo() {
		winch.set(1);
		belt.set(ControlMode.Current, -20);
	}
}
