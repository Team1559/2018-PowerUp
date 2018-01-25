package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain {

	private static final int FR = 0;
	private static final int RR = 1;
	private static final int RL = 2;
	private static final int FL = 3;

	private static final double kP = 0.1;
	private static final double kI = 0.0;
	private static final double kD = 0.0;
	private static final double kF = 0.0;
	private static final int TIMEOUT = 0;

	private boolean isMecanumized;
	private Solenoid solenoid; // TODO: Still needs work (prototypes)
	private VersaDrive drive;
	public WPI_TalonSRX[] motors;

	public DriveTrain(boolean mecanumized) {
		motors = new WPI_TalonSRX[4];
		motors[FL] = new WPI_TalonSRX(Wiring.FL_SRX);
		motors[RL] = new WPI_TalonSRX(Wiring.RL_SRX);
		motors[FR] = new WPI_TalonSRX(Wiring.FR_SRX);
		motors[RR] = new WPI_TalonSRX(Wiring.RR_SRX);
		drive = new VersaDrive(motors[FL], motors[RL], motors[FR], motors[RR]);
		for (int i = 0; i < 4; i++) {
			configTalon(motors[i]);
		}

		solenoid = new Solenoid(1, 0);
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
		
		talon.setNeutralMode(NeutralMode.Brake);
	}
	
	public void resetEncoders() {
		for (WPI_TalonSRX motor : motors) {
			motor.getSensorCollection().setQuadraturePosition(0, TIMEOUT);			
		}		
	}

	public void diagonal(double value) {
		motors[FL].set(ControlMode.Position, Math.sqrt(2) * value);
		motors[RR].set(ControlMode.Position, Math.sqrt(2) * -value);
		motors[FR].set(ControlMode.Position, 0);
		motors[RL].set(ControlMode.Position, 0);
//		motors[FR].set(ControlMode.Position, 0.50 * value);
//		motors[RL].set(ControlMode.Position, 0.50 * -value);
		
	}
	
	public void forward(double value) {
		motors[FL].set(ControlMode.Position, value);
		motors[FR].set(ControlMode.Position, -value);
		motors[RL].set(ControlMode.Position, value);
		motors[RR].set(ControlMode.Position, -value);
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

	public int getAverageEncoderValue() {
		int sum = 0;
		sum += -motors[FR].getSensorCollection().getQuadraturePosition();
		sum += motors[FL].getSensorCollection().getQuadraturePosition();
		sum += -motors[RR].getSensorCollection().getQuadraturePosition();
		sum += motors[RL].getSensorCollection().getQuadraturePosition();
		sum /= 4;
		return sum;
	}

	public void set() {

	}

}
