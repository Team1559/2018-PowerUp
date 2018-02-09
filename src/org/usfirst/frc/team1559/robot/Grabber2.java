package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Grabber2 {
	
	private Solenoid sally;
	
	public Grabber2(int port)
	{
		sally = new Solenoid(port);
	}
	
	public void grab()
	{
		sally.set(true);
	}
	
	public void release()
	{
		sally.set(false);
	}
}
