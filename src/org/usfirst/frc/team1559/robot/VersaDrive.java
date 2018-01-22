package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class VersaDrive extends RobotDriveBase {
	public static final double kDefaultQuickStopThreshold = 0.2;
	public static final double kDefaultQuickStopAlpha = 0.1;

	private static int instances = 0;

	// Curvature Drive variables
	private double m_quickStopThreshold = kDefaultQuickStopThreshold;
	private double m_quickStopAlpha = kDefaultQuickStopAlpha;
	private double m_quickStopAccumulator = 0.0;

	private SpeedController m_frontLeftMotor;
	private SpeedController m_rearLeftMotor;
	private SpeedController m_frontRightMotor;
	private SpeedController m_rearRightMotor;

	private boolean m_reported = false;

	public VersaDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor,
			SpeedController rearRightMotor) {
		m_frontLeftMotor = frontLeftMotor;
		m_rearLeftMotor = rearLeftMotor;
		m_frontRightMotor = frontRightMotor;
		m_rearRightMotor = rearRightMotor;
		addChild(m_frontLeftMotor);
		addChild(m_rearLeftMotor);
		addChild(m_frontRightMotor);
		addChild(m_rearRightMotor);
		instances++;
		setName("VersaDrive", instances);
	}

	public void arcadeDrive(double xSpeed, double zRotation) {
		arcadeDrive(xSpeed, zRotation, true);
	}

	public void arcadeDrive(double xSpeed, double zRotation, boolean squaredInputs) {
		if (!m_reported) {
			HAL.report(tResourceType.kResourceType_RobotDrive, 2, tInstances.kRobotDrive_ArcadeStandard);
			m_reported = true;
		}

		xSpeed = limit(xSpeed);
		xSpeed = applyDeadband(xSpeed, m_deadband);

		zRotation = limit(zRotation);
		zRotation = applyDeadband(zRotation, m_deadband);

		// Square the inputs (while preserving the sign) to increase fine control
		// while permitting full power.
		if (squaredInputs) {
			xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
			zRotation = Math.copySign(zRotation * zRotation, zRotation);
		}

		double leftMotorOutput;
		double rightMotorOutput;

		double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

		if (xSpeed >= 0.0) {
			// First quadrant, else second quadrant
			if (zRotation >= 0.0) {
				leftMotorOutput = maxInput;
				rightMotorOutput = xSpeed - zRotation;
			} else {
				leftMotorOutput = xSpeed + zRotation;
				rightMotorOutput = maxInput;
			}
		} else {
			// Third quadrant, else fourth quadrant
			if (zRotation >= 0.0) {
				leftMotorOutput = xSpeed + zRotation;
				rightMotorOutput = maxInput;
			} else {
				leftMotorOutput = maxInput;
				rightMotorOutput = xSpeed - zRotation;
			}
		}

		m_frontLeftMotor.set(limit(leftMotorOutput) * m_maxOutput);
		m_rearLeftMotor.set(limit(leftMotorOutput) * m_maxOutput);
		m_frontRightMotor.set(-limit(rightMotorOutput) * m_maxOutput);
		m_rearRightMotor.set(-limit(rightMotorOutput) * m_maxOutput);

		m_safetyHelper.feed();
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
		tankDrive(leftSpeed, rightSpeed, true);
	}

	public void tankDrive(double leftSpeed, double rightSpeed, boolean squaredInputs) {
		if (!m_reported) {
			HAL.report(tResourceType.kResourceType_RobotDrive, 2, tInstances.kRobotDrive_Tank);
			m_reported = true;
		}

		leftSpeed = limit(leftSpeed);
		leftSpeed = applyDeadband(leftSpeed, m_deadband);

		rightSpeed = limit(rightSpeed);
		rightSpeed = applyDeadband(rightSpeed, m_deadband);

		// Square the inputs (while preserving the sign) to increase fine control
		// while permitting full power.
		if (squaredInputs) {
			leftSpeed = Math.copySign(leftSpeed * leftSpeed, leftSpeed);
			rightSpeed = Math.copySign(rightSpeed * rightSpeed, rightSpeed);
		}

		m_frontLeftMotor.set(leftSpeed * m_maxOutput);
		m_rearLeftMotor.set(leftSpeed * m_maxOutput);
		m_frontRightMotor.set(-rightSpeed * m_maxOutput);
		m_rearRightMotor.set(-rightSpeed * m_maxOutput);

		m_safetyHelper.feed();
	}

	public void curvatureDrive(double xSpeed, double zRotation, boolean isQuickTurn) {
		if (!m_reported) {
			// HAL.report(tResourceType.kResourceType_RobotDrive, 2,
			// tInstances.kRobotDrive_Curvature);
			m_reported = true;
		}

		xSpeed = limit(xSpeed);
		xSpeed = applyDeadband(xSpeed, m_deadband);

		zRotation = limit(zRotation);
		zRotation = applyDeadband(zRotation, m_deadband);

		double angularPower;
		boolean overPower;

		if (isQuickTurn) {
			if (Math.abs(xSpeed) < m_quickStopThreshold) {
				m_quickStopAccumulator = (1 - m_quickStopAlpha) * m_quickStopAccumulator
						+ m_quickStopAlpha * limit(zRotation) * 2;
			}
			overPower = true;
			angularPower = zRotation;
		} else {
			overPower = false;
			angularPower = Math.abs(xSpeed) * zRotation - m_quickStopAccumulator;

			if (m_quickStopAccumulator > 1) {
				m_quickStopAccumulator -= 1;
			} else if (m_quickStopAccumulator < -1) {
				m_quickStopAccumulator += 1;
			} else {
				m_quickStopAccumulator = 0.0;
			}
		}

		double leftMotorOutput = xSpeed + angularPower;
		double rightMotorOutput = xSpeed - angularPower;

		// If rotation is overpowered, reduce both outputs to within acceptable range
		if (overPower) {
			if (leftMotorOutput > 1.0) {
				rightMotorOutput -= leftMotorOutput - 1.0;
				leftMotorOutput = 1.0;
			} else if (rightMotorOutput > 1.0) {
				leftMotorOutput -= rightMotorOutput - 1.0;
				rightMotorOutput = 1.0;
			} else if (leftMotorOutput < -1.0) {
				rightMotorOutput -= leftMotorOutput + 1.0;
				leftMotorOutput = -1.0;
			} else if (rightMotorOutput < -1.0) {
				leftMotorOutput -= rightMotorOutput + 1.0;
				rightMotorOutput = -1.0;
			}
		}

		m_frontLeftMotor.set(leftMotorOutput * m_maxOutput);
		m_rearLeftMotor.set(leftMotorOutput * m_maxOutput);
		m_frontRightMotor.set(-rightMotorOutput * m_maxOutput);
		m_rearRightMotor.set(-rightMotorOutput * m_maxOutput);

		m_safetyHelper.feed();
	}

	public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
		driveCartesian(ySpeed, xSpeed, zRotation, 0.0);
	}

	public void driveCartesian(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
		if (!m_reported) {
			HAL.report(tResourceType.kResourceType_RobotDrive, 4, tInstances.kRobotDrive_MecanumCartesian);
			m_reported = true;
		}

		ySpeed = limit(ySpeed);
		ySpeed = applyDeadband(ySpeed, m_deadband);

		xSpeed = limit(xSpeed);
		xSpeed = applyDeadband(xSpeed, m_deadband);

		// Compensate for gyro angle.
		Vector2d input = new Vector2d(ySpeed, xSpeed);
		input.rotate(-gyroAngle);

		double[] wheelSpeeds = new double[4];
		wheelSpeeds[MotorType.kFrontLeft.value] = input.x + input.y + zRotation;
		wheelSpeeds[MotorType.kFrontRight.value] = input.x - input.y + zRotation;
		wheelSpeeds[MotorType.kRearLeft.value] = -input.x + input.y + zRotation;
		wheelSpeeds[MotorType.kRearRight.value] = -input.x - input.y + zRotation;

		normalize(wheelSpeeds);

		m_frontLeftMotor.set(wheelSpeeds[MotorType.kFrontLeft.value] * m_maxOutput);
		m_frontRightMotor.set(wheelSpeeds[MotorType.kFrontRight.value] * m_maxOutput);
		m_rearLeftMotor.set(wheelSpeeds[MotorType.kRearLeft.value] * m_maxOutput);
		m_rearRightMotor.set(wheelSpeeds[MotorType.kRearRight.value] * m_maxOutput);

		m_safetyHelper.feed();
	}

	public void drivePolar(double magnitude, double angle, double zRotation) {
		if (!m_reported) {
			HAL.report(tResourceType.kResourceType_RobotDrive, 4, tInstances.kRobotDrive_MecanumPolar);
			m_reported = true;
		}

		driveCartesian(magnitude * Math.sin(angle * (Math.PI / 180.0)), magnitude * Math.cos(angle * (Math.PI / 180.0)),
				zRotation, 0.0);
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("VersaDrive");
		builder.addDoubleProperty("Front Left Motor Speed", m_frontLeftMotor::get, m_frontLeftMotor::set);
		builder.addDoubleProperty("Front Right Motor Speed", m_frontRightMotor::get, m_frontRightMotor::set);
		builder.addDoubleProperty("Rear Left Motor Speed", m_rearLeftMotor::get, m_rearLeftMotor::set);
		builder.addDoubleProperty("Rear Right Motor Speed", m_rearRightMotor::get, m_rearRightMotor::set);
	}

	@Override
	public void stopMotor() {
		m_frontLeftMotor.stopMotor();
		m_frontRightMotor.stopMotor();
		m_rearLeftMotor.stopMotor();
		m_rearRightMotor.stopMotor();
		m_safetyHelper.feed();
	}

	@Override
	public String getDescription() {
		return "VersaDrive";
	}
}
