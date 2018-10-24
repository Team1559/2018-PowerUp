package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.util.MathUtils;
import org.usfirst.frc.team1559.util.PIDFGains;
import org.usfirst.frc.team1559.util.VersaDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class DriveTrain {

	private static final int MAX_RPM = 5300; //this is wrong

	public static final int FR = 0;
	public static final int RR = 1;
	public static final int RL = 2;
	public static final int FL = 3;

	public static final double MAX_SPEED_FPS_TRACTION = 9.67 * 1.01; //6 inch wheels????
	public static final double MAX_TICKS_PER_100MS = MAX_SPEED_FPS_TRACTION * 4096.0 / (Math.PI * Constants.WHEEL_RADIUS_INCHES_TRACTION * 2.0 / 12.0) / 10.0; //2459; // t/100ms = ft/s 
	private static final PIDFGains tractionPidf = new PIDFGains(7.8*1.0/MAX_TICKS_PER_100MS*1024/4, 0, 15.9/5, 1.0/MAX_TICKS_PER_100MS*1024); //TODO retune this boi
	private static final PIDFGains mecanumPidf = tractionPidf;//new PIDFGains(0, 0, 0, 0);
	private static final int TRACTION_PROFILE = 0;
	private static final int MECANUM_PROFILE = 1;
	
	private static final int TIMEOUT = 0;

	private boolean isMecanumized;
	private Solenoid solenoid;
	private VersaDrive drive;
	//private MecanumDrive driveTele;
	public WPI_TalonSRX[] motors;
	
	private boolean manual = false;
	private boolean strafing = false;
	
	private int lifterPos = 1;
	
	private boolean tele = false;
	

	public DriveTrain(boolean mecanumized) {
		motors = new WPI_TalonSRX[4];
		motors[FL] = new WPI_TalonSRX(Wiring.DRV_FL_SRX);
		motors[RL] = new WPI_TalonSRX(Wiring.DRV_RL_SRX);
		motors[FR] = new WPI_TalonSRX(Wiring.DRV_FR_SRX);
		motors[RR] = new WPI_TalonSRX(Wiring.DRV_RR_SRX);
		drive = new VersaDrive(motors[FL], motors[RL], motors[FR], motors[RR]);
		//driveTele = new MecanumDrive(motors[FL], motors[RL], motors[FR], motors[RR]);
		//drive.setMaxOutput(MAX_TICKS_PER_100MS); //JD THIS IS NEW 4/14 //john no, this is bad 4/16/18
		for (int i = 0; i < 4; i++) {
			configTalon(motors[i]);
		}
		drive.setDeadband(0.005); //0.001 for auto
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

	public void setPIDF(double p, double i, double d, double f, int profile) {
		for (int j = 0; j < 4; j++) {
			motors[j].config_kP(profile, p, TIMEOUT);// was 0
			motors[j].config_kI(profile, i, TIMEOUT);
			motors[j].config_kD(profile, d, TIMEOUT);
			motors[j].config_kF(profile, f, TIMEOUT);
		}
	}

	private void configTalon(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);

		talon.configClosedloopRamp(0.0, TIMEOUT); //0.08
		talon.configOpenloopRamp(0.15, TIMEOUT);

		talon.configNominalOutputForward(0, TIMEOUT);
		talon.configNominalOutputReverse(0, TIMEOUT);
		talon.configPeakOutputForward(+1, TIMEOUT);
		talon.configPeakOutputReverse(-1, TIMEOUT);
		
		talon.setInverted(true);
		talon.setSensorPhase(true);
		talon.setNeutralMode(NeutralMode.Coast);
		
		talon.configNeutralDeadband(.04, TIMEOUT);
		
		//TODO this is new
		talon.set(ControlMode.Velocity, 0);
	}
	
	public void setTeleConfig() {
		tele = true;
		drive.setDeadband(0.02);
		for(int i = 0; i <= 3; i++) {
			motors[i].configClosedloopRamp(0.23, TIMEOUT); //0.08
			motors[i].setNeutralMode(NeutralMode.Brake);
			motors[i].set(ControlMode.PercentOutput, 0); //this is here for percent output
		}
	}
	
	public void setAutoConfig() {
		tele = false;
		drive.setDeadband(0.005);
		for (int i = 0; i < 4; i++) {
			configTalon(motors[i]);
		}
	}
	
	public void updateRamp(int position) {
		if (position != lifterPos) {
			lifterPos = position;
			if(position > 2) {
				for(int i = 0; i <= 3; i++) {
					motors[i].configClosedloopRamp(0.15, TIMEOUT); //0.08
				}
			} else if (position > 2) {
				for(int i = 0; i <= 3; i++) {
					motors[i].configClosedloopRamp(0.23, TIMEOUT); //0.08
				}
			}
		}
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
		return (motors[0].getSensorCollection().getQuadraturePosition()- //- for robot 1
				motors[1].getSensorCollection().getQuadraturePosition()+
				motors[2].getSensorCollection().getQuadraturePosition()-
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
	 * </p>z
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
			drive.driveCartesian(0, x * Constants.DT_SPROCKET_RATIO, zRot);
		}
	}
		
	public void driveTele(double x, double y, double zRot) {
		if (isMecanumized) {
			drive.driveCartesianTele(y, x, zRot);
		} else {
			drive.driveCartesianTele(0, x * Constants.DT_SPROCKET_RATIO, zRot);
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
