package org.usfirst.frc.team1559.robot.auto.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//When using this to run profiles make sure to generate it using the pathfinder backend
//When setting the wheel base, use the experimentally determined value of 2.67ft

public class WPI_MP extends Command {

	private ArrayList<Double> headings;
	private ArrayList<Double> velocities;
	private ArrayList<String> commands;

	private int index = 0;
	private int pauseCounter = 0;
	private File file;

	private double softMaxV = 7.2;

	private double pRot = 0.023;// 0.02;//0.037;

	private boolean mirrored;

	public WPI_MP(String filePath, boolean mirrored) {
		file = new File(filePath);
		this.mirrored = mirrored;
	}

	private boolean stop = false;

	@Override
	protected void initialize() {
		Robot.driveTrain.shift(false);
		Robot.driveTrain.setAutoConfig();
		Robot.imu.zeroHeading();
		Object[] arrays = readCSV(file);
		headings = (ArrayList<Double>) arrays[1];
		velocities = (ArrayList<Double>) arrays[0];
		commands = (ArrayList<String>) arrays[2];
	}

	@Override
	protected void execute() {
		
		System.out.println(index);
		
		SmartDashboard.putNumber("Motor 0 Velocity Error", Robot.driveTrain.getMotors()[0].getClosedLoopError(0));
		
		//check if wait command
		if (pauseCounter > 0) {
			pauseCounter -= 20;
			Robot.driveTrain.drive(0, 0, 0);
			return;
		} else {
			pauseCounter = 0;
		}
		
		
		double R = 0;
		double angle = 0;
		double velocity = 0;
		if (headings.get(index) != null)
			angle = headings.get(index); //* (180 / Math.PI); //angles come in as degrees now, also no longer negative
		if (angle - Robot.imu.getHeadingRelative() >= 180) {
			angle -= 360;
		} else if (angle - Robot.imu.getHeadingRelative() <= -180) {
			angle += 360;
		}

		R = -pRot * (angle - Robot.imu.getHeadingRelative());
		if (velocities.get(index) != null) { //is this negative? idk
//			if (Math.abs(velocities.get(index)) >= softMaxV) {
//				velocity = softMaxV;
//			} else {
//				velocity = velocities.get(index);
//			}
			velocity = velocities.get(index);
			Robot.driveTrain.drive(velocity / DriveTrain.MAX_SPEED_FPS_TRACTION / Constants.DT_SPROCKET_RATIO, 0,
					R); // Divide by sprocket ratio because we multiply by it in drive method for tele
			//velocity is negative for robot 2
		}

		// TODO add stuff for commands//
		try {
			if (commands.get(index) != "") {
				switch (commands.get(index).substring(0, 4)) {
				case ("lift"): // lifter
					System.out.println("lifting time");
					new WPI_LifterTo(Integer.parseInt(commands.get(index).substring(4))).start();
					break;
				case ("spit"): // spit
					new WPI_Spit().start();
					break;
				case ("succ"): // spintake (succ(in)(time),(speed)) ex) succt1,1 succf1,2
					//boolean in = commands.get(index).substring(4,5).equals("t");
					//double time = Integer.parseInt(commands.get(index).substring(5, commands.get(index).indexOf(",")));
					//double speed = Integer.parseInt(commands.get(index).substring(commands.get(index).indexOf(",")));
					new WPI_CloseClaw().start();
					new WPI_Spintake(true, 1, 1).start();
					break;
				case ("claw"): // grabber open(o) close(c)
					if (commands.get(index).substring(4).equals("c")) {
						new WPI_CloseClaw().start();
					} else if (commands.get(index).substring(4).equals("o")) {
						new WPI_OpenClaw().start();
					}
					break;
				case ("grab"): // grab(angle in degrees)
					new WPI_RotateShoulder(Integer.parseInt(commands.get(index).substring(4))).start();
					break;
				case ("stop"):
					System.out.println("STOPPING");
					stop = true;
					break;
				case ("wait"): //wait(time in ms)
					pauseCounter = Integer.parseInt(commands.get(index).substring(4));
					break;
				case("shif"):
					Robot.driveTrain.shift();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("incorrect csv data supplied");
		}
		index++;
	}

	@Override
	protected boolean isFinished() {
		return index >= velocities.size() || stop;
	}

	@Override
	protected void end() {
		Robot.driveTrain.drive(0, 0, 0);
	}

	private Object[] readCSV(File file) {
		try {
			Scanner scan = new Scanner(file);
			ArrayList<Double> v = new ArrayList<Double>();
			ArrayList<Double> h = new ArrayList<Double>();
			ArrayList<String> c = new ArrayList<String>();

			String[] record = new String[3];
			while (scan.hasNext()) {
				record = scan.nextLine().split(",");
				try {
					v.add(Double.parseDouble(record[0]));
				} catch (Exception e) {
					v.add(null);
				}
				try {
					if (!mirrored) {
						h.add(Double.parseDouble(record[1]));
					} else {
						h.add(-Double.parseDouble(record[1]));
					}
				} catch (Exception e) {
					h.add(null);
				}
				try {
					c.add(record[2]);
				} catch (Exception e) {
					c.add("");
				}
				//
				// System.out.println(h.get(0));
			}
			return new Object[] { v, h, c };
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to read CSV");
			return null;
		}
	}
}
