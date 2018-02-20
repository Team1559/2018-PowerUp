package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Lifter {

	private WPI_TalonSRX lifterMotor;
	private static final int TIMEOUT = 0;
	private double kP = 14;
	private double kI = 0;
	private double kD = 0;
	private double kF = 0;
	
	public Lifter() {
		lifterMotor = new WPI_TalonSRX(Wiring.LIFT_TALON);
		lifterMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);

//		lifterMotor.configClosedloopRamp(0.4, TIMEOUT);
		lifterMotor.configPeakCurrentLimit(30, TIMEOUT);
		lifterMotor.configContinuousCurrentLimit(30, TIMEOUT);
		lifterMotor.enableCurrentLimit(true);
		
		lifterMotor.configNominalOutputForward(0, TIMEOUT);
		lifterMotor.configNominalOutputReverse(0, TIMEOUT);
		lifterMotor.configPeakOutputForward(+1, TIMEOUT);
		lifterMotor.configPeakOutputReverse(-.2, TIMEOUT);
		
		lifterMotor.config_kP(0, kP, TIMEOUT);
		lifterMotor.config_kI(0, kI, TIMEOUT);
		lifterMotor.config_kD(0, kD, TIMEOUT);
		lifterMotor.config_kF(0, kF, TIMEOUT);

		lifterMotor.setSensorPhase(true);
		lifterMotor.setNeutralMode(NeutralMode.Brake);
	}

	//Positions are negative, pot mounted backwards
	public double getPot() {
		return lifterMotor.getSensorCollection().getAnalogIn();
		//return lifterMotor.getSelectedSensorPosition(0); //this one might be better
	}

	public void toPosition(int x) {
		if (x == 1) {
			lifterMotor.set(ControlMode.Position, -Constants.LIFT_P1_TICKS);
		} else if (x == 2) {
			lifterMotor.set(ControlMode.Position, -Constants.LIFT_P2_TICKS);
		} else if (x == 3) {
			lifterMotor.set(ControlMode.Position, -Constants.LIFT_P3_TICKS);
		} else if (x == 4) {
			lifterMotor.set(ControlMode.Position, -Constants.LIFT_P4_TICKS);
		} else if (x == 5) {
			lifterMotor.set(ControlMode.Position, -Constants.LIFT_P5_TICKS);
		} else {
			System.err.println("Lifter: Invalid lifter position (" + x + ")");
		}
	}
	
	public boolean isAtPosition(int tolerance) {
		return Math.abs(lifterMotor.getClosedLoopError(0)) <= tolerance;
	}
	
	public void driveUp() {
		if (getPot() > Constants.LIFT_UPPER_BOUND) 
			lifterMotor.set(ControlMode.PercentOutput, 0.5);
		else {
			stopMotor();
		}
	}
	
	public void driveDown() {
		if (getPot() < Constants.LIFT_LOWER_BOUND)
			lifterMotor.set(ControlMode.PercentOutput, -0.5);
		else {
			stopMotor();
		}
	}

	public void setMotor(double value) {
		//if (value > 0 && getPot() > Constants.LIFT_UPPER_BOUND)
		lifterMotor.set(ControlMode.PercentOutput, value);
		//else if (value < 0 && getPot() < Constants.LIFT_LOWER_BOUND) {
			//lifterMotor.set(ControlMode.PercentOutput, value);
		//}
		//else {
			//stopMotor();
		//}
	}
	
	public void stopMotor() {
		lifterMotor.set(ControlMode.PercentOutput, 0);
	}
	
	public WPI_TalonSRX getMotor() {
		return lifterMotor;
	}
}
