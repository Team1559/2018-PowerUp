package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.util.MathUtils;
import org.usfirst.frc.team1559.util.VersaDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain {

	private static final int MAX_RPM = 5300;

	public static final int FR = 0;
	public static final int RR = 1;
	public static final int RL = 2;
	public static final int FL = 3;

	private static final int TIMEOUT = 0;

	private boolean isMecanumized;
	private Solenoid solenoid;
	private VersaDrive drive;
	public WPI_TalonSRX[] motors;

	public DriveTrain(boolean mecanumized) {
		motors = new WPI_TalonSRX[4];
		motors[FL] = new WPI_TalonSRX(Wiring.DRV_FL_SRX);
		motors[RL] = new WPI_TalonSRX(Wiring.DRV_RL_SRX);
		motors[FR] = new WPI_TalonSRX(Wiring.DRV_FR_SRX);
		motors[RR] = new WPI_TalonSRX(Wiring.DRV_RR_SRX);
		drive = new VersaDrive(motors[FL], motors[RL], motors[FR], motors[RR]);
		for (int i = 0; i < 4; i++) {
			configTalon(motors[i]);
		}
		drive.setDeadband(0.1);
		solenoid = new Solenoid(0, 0);
		shift(mecanumized);
	}

	public void setPID(double p, double i, double d) {
		setPIDF(p, i, d, 0);
	}

	public void setPIDF(double p, double i, double d, double f) {
		for (int j = 0; j < 4; j++) {
			motors[j].config_kP(0, p, TIMEOUT);
			motors[j].config_kI(0, i, TIMEOUT);
			motors[j].config_kD(0, d, TIMEOUT);
			motors[j].config_kF(0, f, TIMEOUT);
		}
	}

	private void configTalon(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);

		talon.configClosedloopRamp(0.2, TIMEOUT);

		talon.configNominalOutputForward(0, TIMEOUT);
		talon.configNominalOutputReverse(0, TIMEOUT);
		talon.configPeakOutputForward(+1, TIMEOUT);
		talon.configPeakOutputReverse(-1, TIMEOUT);

		talon.setInverted(true);
		talon.setSensorPhase(true);
		talon.setNeutralMode(NeutralMode.Brake);
	}

	public void autoShift() {
		System.out.println(averageRPM());
		double magic = 500;
		if (Math.abs(Robot.oi.getDriverX()) >= 0.2) {
			shift(true);
		} else if (!isMecanumized && averageRPM() > MAX_RPM / 2 + magic) {
			shift(true);
		} else if (isMecanumized && averageRPM() < MAX_RPM / 2 - magic) {
			shift(false);
		}
	}

	private double averageRPM() {
		return MathUtils.average(MathUtils.map((x) -> Math.abs(
				((WPI_TalonSRX) x).getSensorCollection().getQuadratureVelocity() / 4096.0 * 600.0 * 9 * Constants.DT_SPROCKET_RATIO),
				motors));
	}

	public void resetQuadEncoders() {
		for (WPI_TalonSRX motor : motors) {
			motor.getSensorCollection().setQuadraturePosition(0, TIMEOUT);
		}
	}

	// TODO: replace the code please
	public void rotate(double speed) { // slope
		motors[FL].set(ControlMode.PercentOutput, -speed);
		motors[FR].set(ControlMode.PercentOutput, -speed);
		motors[RL].set(ControlMode.PercentOutput, -speed);
		motors[RR].set(ControlMode.PercentOutput, -speed);
	}

	public void setSetpoint(double[] setpoints) {
		assert setpoints.length == 4;
		motors[FL].set(ControlMode.Position, setpoints[FL]);
		motors[FR].set(ControlMode.Position, setpoints[FR]);
		motors[RL].set(ControlMode.Position, setpoints[RL]);
		motors[RR].set(ControlMode.Position, setpoints[RR]);
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
			drive.curvatureDrive(x * Constants.DT_SPROCKET_RATIO, zRot, true);
		}
	}

	public boolean getMecanumized() {
		return isMecanumized;
	}

	public WPI_TalonSRX[] getMotors() {
		return motors;
	}
}
