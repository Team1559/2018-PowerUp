package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Wiring;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Intake {

	private Solenoid solenoid;
	private Spark sparkLeft;
	private Spark sparkRight;
	private Spark sparkRotate;
	
	public Intake() {
		solenoid = new Solenoid(Wiring.NTK_SOLENOID);
		sparkLeft = new Spark(Wiring.NTK_SPARK_LEFT);
		sparkRight = new Spark(Wiring.NTK_SPARK_RIGHT);
		sparkRotate = new Spark(Wiring.NTK_SPARK_ROTATE);
		
	}

	public void open() {
		solenoid.set(true);
	}

	public void close() {
		solenoid.set(false);
	}
	
	public void toggle() {
		solenoid.set(!solenoid.get());
	}

	public void out() {
		sparkLeft.set(-1.0);
		sparkRight.set(1.0);
	}
	
	public void in() {
		sparkLeft.set(1.0);
		sparkRight.set(-1.0);
	}
	
	public void stopIntake() {
		sparkLeft.set(0.0);
		sparkRight.set(0.0);		
	}

	public void stopTread() {
		sparkLeft.set(0);
		sparkRight.set(0);
	}

	public void rotateDown() {
		sparkRotate.set(-0.2);
	}

	public void rotateUp() {
		sparkRotate.set(0.4);
	}
	
	public void rotateStop() {
		sparkRotate.set(0);
	}
}
