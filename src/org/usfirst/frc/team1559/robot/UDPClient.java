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
	 * @return Raw data.
	 */
	public String get() {
		return data;
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
