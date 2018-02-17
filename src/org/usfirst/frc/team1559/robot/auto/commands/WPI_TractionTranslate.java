package org.usfirst.frc.team1559.robot.auto.commands;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1559.util.Calc;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command that drives the robot forward in traction, using WPI implementations
 * 
 * @author Victor Robotics Team 1559, Software
 */
public class WPI_TractionTranslate extends Command {

	private static final double kP = .089; // .089 EVERY .11 you increment it it goes 2 more inches
	private static final double kI = 0.00; // .001
	private static final double kD = 0;

	private double TOLERANCE = 300;
	private double dxInTicks;
	private double x;
	private double averageError;

	private double setpoints[];

	public WPI_TractionTranslate(double x) {
		this.x = x;
		this.dxInTicks = Calc.distanceInTicksTraction(x);
		this.setpoints = new double[4];
	}

	@Override
	protected void initialize() {
		// make sure we're not in mecanum
		Robot.driveTrain.shift(false);
		// change the motors' PID values to the ones here
		Robot.driveTrain.setPID(kP, kI, kD);
		System.out.println("Initializing " + this);
		// reset the encoders
		Robot.driveTrain.resetQuadEncoders();
		setpoints[DriveTrain.FL] = dxInTicks;
		setpoints[DriveTrain.FR] = -dxInTicks;
		setpoints[DriveTrain.RL] = dxInTicks;
		setpoints[DriveTrain.RR] = -dxInTicks;
	}

	@Override
	protected void execute() {
		Robot.driveTrain.setSetpoint(setpoints);
	}

	@Override
	protected boolean isFinished() {
		for (int i = 0; i < 4; i++) {
			averageError += Math.abs(Robot.driveTrain.motors[i].getClosedLoopError(0));
		}
		// actually get the average by dividing by the number of motors, which is four
		averageError /= 4;
		// System.out.println("Traction finished: " + (averageError < TOLERANCE) + " ("
		// + averageError + " < " + TOLERANCE + ")");
		return averageError < TOLERANCE;
	}

	@Override
	protected void end() {
		System.out.println(this + " has finished");
	}

	@Override
	public String toString() {
		return String.format("TractionTranslate(%f inches)", x);
	}

}
