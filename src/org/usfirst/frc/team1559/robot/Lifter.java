package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Lifter
{
	private Potentiometer lift;
	private WPI_TalonSRX lifterino;
	
	public Lifter()
	{
		lift = new AnalogPotentiometer(Wiring.LIFTER_POT);
		lifterino = new WPI_TalonSRX(Wiring.LIFTER_TALON);
	}

	public void liftUp()
	{
		if(lift.get() < Constants.LIFT_TOP_LIMIT)
		{
			lifterino.set(Constants.LIFT_SPEED);
		}
		else
		{
			lifterino.set(0);
		}
		
	}
	public void goDown()
	{
		if(lift.get() > Constants.LIFT_BOTTOM_LIMIT)
		{
			lifterino.set(-Constants.LIFT_SPEED);
		}
		else
		{
			lifterino.set(0);
		}
		
	}
}
