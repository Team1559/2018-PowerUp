package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain {

	private boolean isMecanumized;
	private Solenoid solenoid; // TODO: Still needs work (prototypes)
	private VersaDrive drive;

	public DriveTrain(boolean mecanumized) {
		WPI_TalonSRX frontLeft = new WPI_TalonSRX(Wiring.FL_SRX);
		WPI_TalonSRX rearLeft = new WPI_TalonSRX(Wiring.RL_SRX);
		WPI_TalonSRX frontRight = new WPI_TalonSRX(Wiring.FR_SRX);
		WPI_TalonSRX rearRight = new WPI_TalonSRX(Wiring.RR_SRX);

		drive = new VersaDrive(frontLeft, rearLeft, frontRight, rearRight);
		solenoid = new Solenoid(1, 0);
	}

	/**
	 * <p>
	 * Sets {@link #isMecanumized} and {@link #solenoid}'s output to the inverse of
	 * {@link #isMecanumized}
	 * </p>
	 * <p>
	 * For example, if {@link #isMecanumized} is <i>true</i>, then
	 * {@link #solenoid}'s output will be disabled and {@link #isMecanumized} will
	 * set to false
	 * </p>
	 */
	public void shift() {
		shift(!isMecanumized);
	}

	/**
	 * Sets {@link #isMecanumized} and {@link #solenoid}'s output
	 * 
	 * @param b
	 *            The value to set {@link #isMecanumized} to and whether or not
	 *            {@link #solenoid} has its output enabled
	 */
	public void shift(boolean b) {
		isMecanumized = b;
		solenoid.set(b);
	}

	/**
	 * <p>
	 * Drive the robot forwards (positive) or backwards (negative)
	 * </p>
	 * <p>
	 * This also will depend on the value of {@link #isMecanumized}
	 * </p>
	 * 
	 * @param x
	 *            Speed along the x-axis
	 * @param y
	 *            Speed along the y-axis
	 * @param zRot
	 *            Rotation along the z-axis
	 */
	public void drive(double x, double y, double zRot) {
		if (isMecanumized) {
			drive.driveCartesian(y, x, zRot);
		} else {
			drive.curvatureDrive(x, zRot, true);
		}
	}
}
