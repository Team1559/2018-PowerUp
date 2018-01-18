package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class DriveTrain {

	private boolean mecanumized;
	private Solenoid iHaveToGetWorkDoneRyan;
	private DifferentialDrive diffDrive;
	private MecanumDrive mecaDrive;

	public DriveTrain(boolean mecanumized) {
		WPI_TalonSRX frontLeft = new WPI_TalonSRX(Wiring.FL_SRX);
		WPI_TalonSRX rearLeft = new WPI_TalonSRX(Wiring.RL_SRX);
		SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, rearLeft);

		WPI_TalonSRX frontRight = new WPI_TalonSRX(Wiring.FR_SRX);
		WPI_TalonSRX rearRight = new WPI_TalonSRX(Wiring.RR_SRX);
		SpeedControllerGroup right = new SpeedControllerGroup(frontRight, rearRight);

		diffDrive = new DifferentialDrive(left, right);
		mecaDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
	}
	
	public void shift() {
		shift(!mecanumized);
	}

	public void shift(boolean b) {
		mecanumized = b;
		iHaveToGetWorkDoneRyan.set(b);
	}

	public void drive(double x, double y, double zRot) {
		if (mecanumized) {
			mecaDrive.driveCartesian(y, x, zRot);
		} else {
			diffDrive.arcadeDrive(x, zRot);
		}
	}
}
