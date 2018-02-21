package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Wiring;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Intake {

	private Solenoid solenoid;
	private Spark sparkLeft;
	private Spark sparkRight;
	private Spark sparkRotate;

	private boolean isGoingDown;
	private boolean rotateActive;

	public Intake() {
		solenoid = new Solenoid(Wiring.NTK_SOLENOID);
		sparkLeft = new Spark(Wiring.NTK_SPARK_LEFT);
		sparkRight = new Spark(Wiring.NTK_SPARK_RIGHT);
		sparkRotate = new Spark(Wiring.NTK_SPARK_ROTATE);
		isGoingDown = false;
		rotateActive = false;
	}

	public void open() {
		solenoid.set(false);
	}

	public void close() {
		solenoid.set(true);
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

	public void rotateIntake() {
		sparkLeft.set(1.0);
		sparkRight.set(1.0);
	}

	public void stopIntake() {
		sparkLeft.set(0.0);
		sparkRight.set(0.0);
	}

	public void rotateDown() {
		isGoingDown = true;
	}

	public void rotateUp() {
		isGoingDown = false;
	}

	public void setActive(boolean b) {
		rotateActive = b;
	}

	public void updateRotate() {
		if (isGoingDown) {
			if (rotateActive) {
				sparkRotate.set(-0.4);
			} else {
				sparkRotate.set(-0.15);
			}
		} else {
			if (rotateActive) {
				sparkRotate.set(0.65);
			} else {
				sparkRotate.set(0.268);
			}
		}

	}

	public void switchState() {
		isGoingDown = !isGoingDown;
	}

	public void rotateStop() {
		sparkRotate.set(0);
	}
}
