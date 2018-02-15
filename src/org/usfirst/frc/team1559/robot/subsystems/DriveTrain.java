package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.VersaDrive;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain {

	public static final int FR = 0;
	public static final int RR = 1;
	public static final int RL = 2;
	public static final int FL = 3;

	// 0.13 for mecanum
	public static double kP = .01;
	private static final double kI = 0.0005;
	// 4 for short and long
	private static final double kD = 0.03;
	private static final double kF = 0;
	private static final int TIMEOUT = 0;

	private boolean isMecanumized;
	private Solenoid solenoid;
	private VersaDrive drive;
	public WPI_TalonSRX[] motors;

	/**
	 * Creates a new drive train, and initializes all of the talons
	 * 
	 * @param mecanumized
	 *            Whether or not the chassis should be set to mecanum
	 */
	public DriveTrain(boolean mecanumized) {
		motors = new WPI_TalonSRX[4];
		motors[FL] = new WPI_TalonSRX(Wiring.DRV_FL_SRX);
		motors[RL] = new WPI_TalonSRX(Wiring.DRV_RL_SRX);
		motors[FR] = new WPI_TalonSRX(Wiring.DRV_FR_SRX);
		motors[RR] = new WPI_TalonSRX(Wiring.DRV_RR_SRX);
		drive = new VersaDrive(motors[FL], motors[RL], motors[FR], motors[RR]);
		for (WPI_TalonSRX motor : motors)
			configTalon(motor);
		drive.setDeadband(0.1);
		solenoid = new Solenoid(0, 0);
	}

	private void configTalon(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);

		talon.configNominalOutputForward(0, TIMEOUT);
		talon.configNominalOutputReverse(0, TIMEOUT);
		talon.configPeakOutputForward(+1, TIMEOUT);
		talon.configPeakOutputReverse(-1, TIMEOUT);

		talon.config_kP(0, kP, TIMEOUT);
		talon.config_kI(0, kI, TIMEOUT);
		talon.config_kD(0, kD, TIMEOUT);
		talon.config_kF(0, kF, TIMEOUT);

		talon.setInverted(true);
		talon.setSensorPhase(true);
		talon.setNeutralMode(NeutralMode.Brake);
	}

	public void resetQuadEncoders() {
		for (WPI_TalonSRX motor : motors) {
			motor.getSensorCollection().setQuadraturePosition(0, TIMEOUT);
		}
	}

	/**
	 * Calls {@link #translateRotate(double, double, double)} with the angle set to
	 * zero
	 * 
	 * @param x
	 *            Change in the x-axis (forward or backward)
	 * @param y
	 *            Change in the y-axis (left or right)
	 */
	public void translateAbsolute(double x, double y) {
		translateRotate(x, y, 0);
	}

	/**
	 * Calls {@link #setMotors(ControlMode, double)} with
	 * {@link com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput
	 * ControlMode.PercentOuput} and the given speed value
	 * 
	 * @param speed
	 *            How fast to rotate
	 */
	public void rotate(double speed) {
		setMotors(ControlMode.PercentOutput, speed);
	}

	/**
	 * Calls {@link #setMotors(ControlMode, double[])} with
	 * {@link com.ctre.phoenix.motorcontrol.ControlMode.Position
	 * ControlMode.Position}, the given x and y, and the given angle (all calculated
	 * with variations of x + y - angle)
	 * 
	 * @param x
	 *            Change in the x-axis (forward or backward)
	 * @param y
	 *            Change in the y-axis (left or right)
	 * @param angle
	 *            Rotational change in degrees
	 */
	public void translateRotate(double x, double y, double angle) {
		setMotors(ControlMode.Position,
				new double[] { (x + y - angle), (-x + y - angle), (x - y - angle), (-x - y - angle) });
	}

	public void setpoint(double[] setpoints) {
		assert setpoints.length == 4;
		setMotors(ControlMode.Position, new double[] { setpoints[FL], setpoints[FR], setpoints[RL], setpoints[RR] });
	}

	/**
	 * Calls {@link #setMotors(ControlMode, double[])} with the given value
	 * 
	 * @param mode
	 *            The {@link com.ctre.phoenix.motorcontrol.ControlMode ControlMode}
	 *            to use
	 * @param value
	 *            The numerical value to use for all four of the motors
	 */
	private void setMotors(ControlMode mode, double value) {
		setMotors(mode, new double[] { value, value, value, value });
	}

	/**
	 * Sets all four of the motors to the given values in the respective order
	 * 
	 * @param mode
	 *            The {@link com.ctre.phoenix.motorcontrol.ControlMode ControlMode}
	 *            to use
	 * @param value
	 *            The numerical value to use for all four of the motors
	 */
	private void setMotors(ControlMode mode, double[] values) {
		motors[FL].set(mode, values[0]);
		motors[FR].set(mode, values[1]);
		motors[RL].set(mode, values[2]);
		motors[RR].set(mode, values[3]);
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
		System.out.println("Shifting! (solenoid being enabled: " + b + ")");
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
	 *            Change in the x-axis (forward or backward)
	 * @param y
	 *            Change in the y-axis (left or right)
	 * @param zRot
	 *            Rotation along the z-axis (turning)
	 */
	public void drive(double x, double y, double zRot) {
		if (isMecanumized) {
			drive.driveCartesian(y, x, zRot);
		} else {
			drive.curvatureDrive(x, zRot, true);
		}
	}

	public boolean getMecanumized() {
		return isMecanumized;
	}

	public WPI_TalonSRX[] getMotors() {
		return motors;
	}
}
