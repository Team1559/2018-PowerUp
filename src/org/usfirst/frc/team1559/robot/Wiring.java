package org.usfirst.frc.team1559.robot;

public interface Wiring {
	// Drive Train
	final int FR_SRX = 11;
	final int RR_SRX = 13;
	final int RL_SRX = 12;
	final int FL_SRX = 10;
	
	final int JOY_DRIVER = 0;
	final int JOY_COPILOT = 1;
	
	// TODO: Find values. (Zeroes below are placeholders).
	
	// Gatherer
	final int GATHERER_TALON = 0;
	final int GATHERER_SOLENOID = 0;
	// Lifter
	final int LIFTER_TALON = 0;
	// Climber
	final int CLIMBER_SOLENOID = 0;
	// Controller
	final int BTN_CLIMB = 0;
	final int BTN_LIFT = 0;
	final int BTN_GRAB = 0;
}
