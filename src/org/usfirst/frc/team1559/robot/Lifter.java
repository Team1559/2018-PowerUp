package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Lifter
{
	private Potentiometer lift;
	private final int TOP_LIMIT = 1000;
	private final int BOTTOM_LIMIT = 0;
	private WPI_TalonSRX lifterino;
	
	public Lifter()
	{
		lift = new AnalogPotentiometer(Wiring.LIFTER_POT);
				
	}

	public void liftUp()
	{
		if(lift.get() < TOP_LIMIT)
		{
			lifterino.set(50.0);
		}
		else
		{
			lifterino.set(0.0);
		}
		
	}
	public void goDown()
	{
		if(lift.get() > BOTTOM_LIMIT)
		{
			lifterino.set(-50.0);
		}
		else
		{
			lifterino.set(0.0);
		}
		
	}
}
