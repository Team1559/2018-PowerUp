package org.usfirst.frc.team1559.robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This class is responsible for communicating to a server over UDP.
 */
public class UDPClient implements Runnable {

	private static final String HOST = "10.15.59.6"; // 169.254.227.6
	private static final int PORT = 5801;

	Thread clientThread;
	boolean running;

	String data;

	public UDPClient() {
		clientThread = new Thread(this);
		clientThread.start();
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			String rec = receive();
			 System.out.println("from jetson: " + rec);
			if (rec != null) {
				data = rec;
			}
		}
	}

	public String get() {
		return data;
	}

	public String getX() {
		return data.substring(data.indexOf('x'),data.indexOf('y'));
	}
	
	public String getY() {
		return data.substring(data.indexOf('y'),data.indexOf('r'));
	}

	public String getR() {
		return data.substring(data.indexOf('r')+1);
	}

	public String receive() {
		String ret = null;
		try {
			Socket clientSocket = new Socket(HOST, PORT);
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			ret = inFromServer.readLine();
			clientSocket.close();
		} catch (Exception e) {

		}

		return ret;
	}

}
