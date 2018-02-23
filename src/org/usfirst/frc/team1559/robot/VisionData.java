package org.usfirst.frc.team1559.robot;

public class VisionData {

	public char id;
	public double angle;
	public double distance;
	
	public VisionData(char id, double angle, double distance) {
		this.id = id;
		this.angle = angle;
		this.distance = distance;
	}

	public static VisionData fromString(String s) {
		char id = s.charAt(0);
		s = s.substring(1);
		String[] tokens = s.split(",");
		double angle = Double.parseDouble(tokens[0]);
		double distance = Double.parseDouble(tokens[1]);
		return new VisionData(id, angle, distance);
	}
}
