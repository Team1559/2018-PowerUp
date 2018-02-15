package org.usfirst.frc.team1559.robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This class is responsible for communicating to a server over UDP (User
 * Datagram Protocol)
 */
public class UDPClient implements Runnable {

	// 169.254.227.6
	private static final String HOST = "10.15.59.6";
	private static final int PORT = 5801;

	private boolean running;

	private Thread clientThread;
	private String data;

	public UDPClient() {
		clientThread = new Thread(this);
		clientThread.start();
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			data = receive();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the raw data from the UDP connection
	 * 
	 * @return Raw data from UDP, null if there's an error
	 */
	public String getData() {
		return data;
	}

	/**
	 * Returns the data from the UDP connection as a string (similar to
	 * {@link #getData()})
	 * 
	 * @return The data as a string from the UDP socket stream, or <code>null</code>
	 *         with there is an error
	 */
	private String receive() {
		String recieved = null;
		try {
			Socket clientSocket = new Socket(HOST, PORT);
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			recieved = inFromServer.readLine();
			clientSocket.close();
		} catch (Exception e) {
			System.out.println("There was an error while trying to receive data from the UDP socket stream!");
			e.printStackTrace();
		}
		return recieved;
	}

}
