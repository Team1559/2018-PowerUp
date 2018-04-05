package org.usfirst.frc.team1559.util;

public class PIDFGains {

	public final double kP, kI, kD, kF;

	public PIDFGains(double kP, double kI, double kD, double kF) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
	}
}
