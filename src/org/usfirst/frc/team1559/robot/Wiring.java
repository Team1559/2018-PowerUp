package org.usfirst.frc.team1559.robot;

public interface Wiring {
	// Drive Train
	public static final int DRV_FR_SRX = 11;
	public static final int DRV_RR_SRX = 10; // actual robot = 10, last year = 13
	public static final int DRV_RL_SRX = 12;
	public static final int DRV_FL_SRX = 13; // actual robot = 13, last year = 10
	
	// Lifter
	public static final int LFT_POT = 0;
	public static final int LFT_TALON = 0;

	// Intake
	public static final int NTK_TALON = 0;
	public static final int NTK_SOLENOID = 0;
	// Climber
	public static final int CLM_SPARK = 0;
	public static final int CLM_TALON = 0;
	public static final int CLM_LIMIT_ID = 0;
	// Controllers
	public static final int JOY_DRIVER = 0;
	public static final int JOY_COPILOT = 1;
	
	public static final int BTN_CLIMB_EXPAND = 0;
	public static final int BTN_CLIMB_RETRACT = 0;
	public static final int BTN_LIFT_SWITCH = 0;
	public static final int BTN_LIFT_GROUND = 0;
	public static final int BTN_LIFT_SCALE_POS_ONE = 0;
	public static final int BTN_LIFT_SCALE_POS_TWO = 0;
	public static final int BTN_LIFT_SCALE_POS_THREE = 0;
	public static final int BTN_GRAB = 0;
}
