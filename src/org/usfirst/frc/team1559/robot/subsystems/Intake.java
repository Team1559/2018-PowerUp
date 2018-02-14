package org.usfirst.frc.team1559.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Intake {

	private Solenoid solenoid;
	private Spark spark1;
	private Spark spark2;
	private Spark spark3;

	public Intake(int... ports) {
		assert ports.length == 4;
		solenoid = new Solenoid(ports[0]);
		spark1 = new Spark(ports[1]);
		spark2 = new Spark(ports[2]);
		spark3 = new Spark(ports[3]);
	}

	public void open() {
		solenoid.set(true);
	}

	public void close() {
		solenoid.set(false);
	}

	public void startTread() {
		spark1.set(1.0);
		spark2.set(1.0);
	}

	public void stopTread() {
		spark1.set(0);
		spark2.set(0);
	}

	public void up() {
		spark3.set(1.0);
		// spark4.set(1.0);
	}

	public void down() {
		spark3.set(-1.0);
		// spark4.set(-1.0);
	}

	public void stop() {
		spark3.set(0);
		// spark4.set(0);
	}
}
