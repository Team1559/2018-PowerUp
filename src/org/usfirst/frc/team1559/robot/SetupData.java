package org.usfirst.frc.team1559.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetupData {

	private int position;
	private NetworkTableEntry positionEntry;
	
	public SetupData() {
//		NetworkTableInstance inst = NetworkTableInstance.getDefault();
//		NetworkTable table = inst.getTable("Setup/Position");
//		positionEntry = table.getEntry("Position");
//		position = (int) positionEntry.getDouble(0);
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int number) {
//		positionEntry.setNumber(number);
		position = number;
	}
	
	public void updateData() {
		int pos = (int) SmartDashboard.getNumber("Position", 0);
		//positionEntry.setDouble(pos);
		position = pos;
	}
	
}
