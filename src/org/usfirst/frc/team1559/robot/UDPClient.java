package org.usfirst.frc.team1559.robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This class is responsible for communicating to a server over UDP.
 */
public class UDPClient implements Runnable {

	private static final String HOST = "10.15.59.6";
	private static final int PORT = 5801;

	private Thread clientThread;
	private boolean running;

	private String data;

	public UDPClient() {
		clientThread = new Thread(this);
		clientThread.start();
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			String rec = receive();
			if (rec != null) {
				data = rec;
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This is the method you should use.
	 * 
	 * @return Raw data.
	 */
	public String get() {
		return data;
	}

	// don't actually use this, rip
	public String getID() {
		return data.substring(0, 1);
	}

	public double getAngle() {
		return Double.parseDouble(data.substring(1, data.indexOf(",")));
	}

	public double getDistance() {
		return Double.parseDouble(data.substring(data.indexOf(",") + 1));
	}

	private String receive() {
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
