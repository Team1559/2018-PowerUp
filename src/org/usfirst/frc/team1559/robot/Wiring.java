package org.usfirst.frc.team1559.robot;

public interface Wiring {
	// Drive Train
	final int DRV_FR_SRX = 11;
	final int DRV_RR_SRX = 10; // actual robot = 10, last year = 13
	final int DRV_RL_SRX = 12;
	final int DRV_FL_SRX = 13; // actual robot = 13, last year = 10
	
	// Lifter
	final int LFT_POT = 0;
	final int LFT_TALON = 0;

	// Intake
	final int NTK_TALON = 0;
	final int NTK_SOLENOID = 0;
	// Climber
	final int CLM_SPARK = 0;
	final int CLM_TALON = 0;
	final int CLM_LIMIT_ID = 0;
	// Controllers
	final int JOY_DRIVER = 0;
	final int JOY_COPILOT = 1;
	
	final int BTN_CLIMB_EXPAND = 0;
	final int BTN_CLIMB_RETRACT = 0;
	final int BTN_LIFT = 0;
	final int BTN_GRAB = 0;
}
