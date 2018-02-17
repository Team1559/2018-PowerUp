package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1559.util.Calc;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command that drives the robot forward, sideways, or both, using WPI implementations
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class WPI_MecanumTranslate extends Command {

	private static final double kP = .125;
	private static final double kI = 0;
	private static final double kD = 0;

	public static int incrementalError[] = new int[4];

	private final double minTime = 0.25;
	private double TOLERANCE = 300;
	private double dxInTicks, dyInTicks;
	private double x, y;
	private double startTime;
	private double averageError;

	private double angle;
	private double angleInTicks;
	private double setpoints[];

	public WPI_MecanumTranslate(double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.dyInTicks = Calc.distanceInTicksMecanum(y);
		this.dxInTicks = Calc.distanceInTicksMecanum(x);
		this.angleInTicks = Calc.angleInTicks(angle);
		this.setpoints = new double[4];
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.shift(true);
		Robot.driveTrain.setPID(kP, kI, kD);
		
		System.out.println("Initializing " + this);
		
		startTime = Timer.getFPGATimestamp();
		// reset the encoders before each command starts
		Robot.driveTrain.resetQuadEncoders();
		
		// set each setpoint to the calculated value
		setpoints[DriveTrain.FL] = dxInTicks + dyInTicks - angleInTicks - incrementalError[DriveTrain.FL];
		setpoints[DriveTrain.FR] = -dxInTicks + dyInTicks - angleInTicks - incrementalError[DriveTrain.FR];
		setpoints[DriveTrain.RL] = dxInTicks - dyInTicks - angleInTicks - incrementalError[DriveTrain.RL];
		setpoints[DriveTrain.RR] = -dxInTicks - dyInTicks - angleInTicks - incrementalError[DriveTrain.RR];
	}

	@Override
	protected void execute() {
		// called repeatedly until the command finishes
		Robot.driveTrain.setSetpoint(setpoints);
	}

	@Override
	protected boolean isFinished() {
		if (Timer.getFPGATimestamp() < startTime + minTime)
			return false; // took too long!

		for (int i = 0; i < 4; i++) {
			// add each motor error together, changing to positive if needed (with absolute value)
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		// actually get the average by dividing by the number of motors, which is four
		averageError /= 4;
		
		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println(this + " has finished");
		for (int i = 0; i < 4; i++) {
			// add to the incremental error for each motor (0 through 3) with its closed loop error
			incrementalError[i] = Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
	}

	@Override
	public String toString() {
		return String.format("MecanumTranslate (%f, %f, %f degrees)", x, y, angle);
	}

}
