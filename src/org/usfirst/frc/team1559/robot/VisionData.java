package org.usfirst.frc.team1559.robot;

public class VisionData {

	public double angle;
	public double distance;

	public void parseString(String s) {
		String[] tokens = s.split(",");
		this.angle = Double.parseDouble(tokens[0]);
		this.distance = Double.parseDouble(tokens[1]);
	}

}
