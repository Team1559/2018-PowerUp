package org.usfirst.frc.team1559.robot;

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
		PWMTalonSRX frontLeft = new PWMTalonSRX(Wiring.FL_SRX);
		PWMTalonSRX rearLeft = new PWMTalonSRX(Wiring.RL_SRX);
		SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, rearLeft);

		PWMTalonSRX frontRight = new PWMTalonSRX(Wiring.FR_SRX);
		PWMTalonSRX rearRight = new PWMTalonSRX(Wiring.RR_SRX);
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
