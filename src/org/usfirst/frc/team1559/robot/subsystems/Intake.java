package org.usfirst.frc.team1559.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Intake {

	private Solenoid sally;
	private Spark sparky1;
	private Spark sparky2;
	private Spark sparkus1;
	private Spark sparkus2;

	public Intake(int port1, int port2, int port3, int port4, int port5) {
		sally = new Solenoid(port1);
		sparky1 = new Spark(port2);
		sparky2 = new Spark(port3);
		sparkus1 = new Spark(port4);
		sparkus2 = new Spark(port5);
	}

	public void open() // Opens and closes pincers while turning belt
	{
		sally.set(true);
	}

	public void close() {
		sally.set(false);
	}

	public void startTread() {
		sparky1.set(1.0);
		sparky2.set(1.0);
	}

	public void stopTread() {
		sparky1.set(0);
		sparky2.set(0);
	}

	public void up() // Lift and lower grabber
	{
		sparkus1.set(1.0);
		sparkus2.set(1.0);
	}

	public void down() {
		sparkus1.set(-1.0);
		sparkus2.set(-1.0);
	}

	public void stop() {
		sparkus1.set(0);
		sparkus2.set(0);
	}
}
