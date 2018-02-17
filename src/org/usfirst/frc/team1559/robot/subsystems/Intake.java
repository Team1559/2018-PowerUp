package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Wiring;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Intake {

	private Solenoid solenoid;
	private Spark sparkLeft;
	private Spark sparkRight;
	private Spark sparkRotate;
	
	private boolean isDown;
	
	
	public Intake() {
		solenoid = new Solenoid(Wiring.NTK_SOLENOID);
		sparkLeft = new Spark(Wiring.NTK_SPARK_LEFT);
		sparkRight = new Spark(Wiring.NTK_SPARK_RIGHT);
		sparkRotate = new Spark(Wiring.NTK_SPARK_ROTATE);
		
		isDown = true;
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
		sparkRotate.set(-0.3);
	}

	public void rotateUp() {
		sparkRotate.set(0.8);
	}
	
	public void switchState() {
		isDown = !isDown;
	}
	
	public void rotate() {
		if (!isDown) {
			sparkRotate.set(0.268);
		}
		else {
			rotateStop();
		}
	}
	
	public void setState(boolean b) {
		isDown = b;
	}
	
	public void rotateStop() {
		sparkRotate.set(0);
	}
}
