package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.util.MathUtils;
import org.usfirst.frc.team1559.util.PIDFGains;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class DriveTrain {

	private static final int MAX_RPM = 5300;

	public static final int FR = 0;
	public static final int RR = 1;
	public static final int RL = 2;
	public static final int FL = 3;
	
	private static final PIDFGains tractionPidf = new PIDFGains(2.11, 5.9, 0, 0.4);
	private static final PIDFGains mecanumPidf = new PIDFGains(2.11, 5.9, 0, 0.4);
	private static final int TRACTION_PROFILE = 0;
	private static final int MECANUM_PROFILE = 1;
	
	private static final int TIMEOUT = 0;

	private boolean isMecanumized;
	private Solenoid solenoid;
	//private VersaDrive drive;
	private MecanumDrive drive;
	public WPI_TalonSRX[] motors;
	
	private boolean manual = false;
	private boolean strafing = false;
	

	public DriveTrain(boolean mecanumized) {
		motors = new WPI_TalonSRX[4];
		motors[FL] = new WPI_TalonSRX(Wiring.DRV_FL_SRX);
		motors[RL] = new WPI_TalonSRX(Wiring.DRV_RL_SRX);
		motors[FR] = new WPI_TalonSRX(Wiring.DRV_FR_SRX);
		motors[RR] = new WPI_TalonSRX(Wiring.DRV_RR_SRX);
		//drive = new VersaDrive(motors[FL], motors[RL], motors[FR], motors[RR]);
		drive = new MecanumDrive(motors[FL], motors[RL], motors[FR], motors[RR]);
		for (int i = 0; i < 4; i++) {
			configTalon(motors[i]);
		}
		drive.setDeadband(0.2);
		solenoid = new Solenoid(0, 0);
		shift(mecanumized);
		setPIDF(tractionPidf.kP, tractionPidf.kI, tractionPidf.kD, tractionPidf.kF, TRACTION_PROFILE);
		setPIDF(mecanumPidf.kP, mecanumPidf.kI, mecanumPidf.kD, mecanumPidf.kF, MECANUM_PROFILE);
	}
	
	public void setProfile(int profile) {
		for(int i = 0; i <= 3; i++) {
			motors[0].selectProfileSlot(profile, 0);
		}
	}

	public void setPIDF(double p, double i, double d, double f, double profile) {
		for (int j = 0; j < 4; j++) {
			motors[j].config_kP(0, p, TIMEOUT);
			motors[j].config_kI(0, i, TIMEOUT);
			motors[j].config_kD(0, d, TIMEOUT);
			motors[j].config_kF(0, f, TIMEOUT);
		}
	}

	private void configTalon(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);

		talon.configClosedloopRamp(0.08, TIMEOUT); //0.2
		talon.configOpenloopRamp(0.15, TIMEOUT);

		talon.configNominalOutputForward(0, TIMEOUT);
		talon.configNominalOutputReverse(0, TIMEOUT);
		talon.configPeakOutputForward(+1, TIMEOUT);
		talon.configPeakOutputReverse(-1, TIMEOUT);
		
		talon.setInverted(true);
		talon.setSensorPhase(true);
		talon.setNeutralMode(NeutralMode.Brake);
		
		//TODO this is new
		talon.set(ControlMode.Velocity, 0);
	}

	public void autoShift() {
		if (true) {
			double magic = 500;
			if (Math.abs(Robot.oi.getDriverY()) >= 0.1) {
				this.strafing = false;
			}
			if (Math.abs(Robot.oi.getDriverX()) >= 0.15) {
				shift(true);
				this.strafing = true;
			} else if (!isMecanumized && getAverageRPM() > MAX_RPM / 2 + magic) {
				shift(true);
			} else if (isMecanumized && getAverageRPM() < MAX_RPM / 2 - (1.5*magic) && !this.strafing) {
				shift(false);
			}
		}
	}
	
	public void setManual(boolean b) {
		manual = b;
	}
	
	public void toggleManual() {
		manual = !manual;
	}
	
	public boolean isManual() {
		return manual;
	}

	public double getAveragePosition() {
		//TODO change this for robot 1 encoders, idk why its neg
		return (motors[0].getSensorCollection().getQuadraturePosition()+ //- for robot 1
				motors[1].getSensorCollection().getQuadraturePosition()-
				motors[2].getSensorCollection().getQuadraturePosition()+
				motors[3].getSensorCollection().getQuadraturePosition())/4.0;
	}
	
	
	public double getAverageRPM() {
		return MathUtils.average(MathUtils.map((x) -> Math.abs(
				((WPI_TalonSRX) x).getSensorCollection().getQuadratureVelocity() / 4096.0 * 600.0 * 9 * Constants.DT_SPROCKET_RATIO),
				motors));
	}
	
	public double getAbsoluteAverageRPM() {
		return MathUtils.average(MathUtils.map((x) -> Math.abs(
				Math.abs(((WPI_TalonSRX) x).getSensorCollection().getQuadratureVelocity() / 4096.0 * 600.0 * 9 * Constants.DT_SPROCKET_RATIO)),
				motors));
	}

	public void resetQuadEncoders() {
		System.out.println("resetting quad encoders");
		for (int i = 0; i < motors.length; i++) {
			motors[i].getSensorCollection().setQuadraturePosition(0, TIMEOUT);
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
		//System.out.println("Shifting! (solenoid being enabled: " + b + ")");
		isMecanumized = b;
		solenoid.set(b);
		
		if(isMecanumized) {
			setProfile(MECANUM_PROFILE);
		} else {
			setProfile(TRACTION_PROFILE);
		}
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
			//drive.driveCartesianVelocity(y, x, zRot);
			drive.driveCartesian(y, x, zRot);
		} else {
			//drive.curvatureDriveVelocity(x * Constants.DT_SPROCKET_RATIO, zRot, true);
			drive.driveCartesian(0, x, zRot);
		}
	}
	
	public int getEncoder(int id) {
		return motors[id].getSensorCollection().getQuadraturePosition();
	}

	public boolean getMecanumized() {
		return isMecanumized;
	}

	public WPI_TalonSRX[] getMotors() {
		return motors;
	}
}
