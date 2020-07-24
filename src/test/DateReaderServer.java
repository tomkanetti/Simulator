package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class DateReaderServer {

	private int port;
	private int timeInSec;
	private volatile boolean stop;
	public boolean connected = false;

	public DateReaderServer(int port, int timeInSec) {
		this.port = port;
		this.timeInSec = timeInSec;
		this.stop = false;
		this.start(port);
	}

	public void stop() {
		stop = true;
	}

	public void runServer() {

		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Server Opened");
			System.out.println("Waiting For A Client...");
			server.setSoTimeout(10000000);
			Socket client;
			try {
				client = server.accept();
				connected = true;
				System.out.println("FlightGear Connected Successfully");

				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String line = null;
				while (!stop) {
					line = in.readLine();
					String[] values = line.split(","); // add value to the simmap by default
					Interpreter.symMap.replace("/instrumentation/airspeed-indicator/indicated-speed-kt",
							new Double(values[0]));
					Interpreter.symMap.replace("/instrumentation/altimeter/indicated-altitude-ft",
							new Double(values[1]));
					Interpreter.symMap.replace("/instrumentation/altimeter/pressure-alt-ft", new Double(values[2]));
					Interpreter.symMap.replace("/instrumentation/attitude-indicator/indicated-pitch-deg",
							new Double(values[3]));
					Interpreter.symMap.replace("/instrumentation/attitude-indicator/indicated-roll-deg",
							new Double(values[4]));
					Interpreter.symMap.replace("/instrumentation/attitude-indicator/internal-pitch-deg",
							new Double(values[5]));
					Interpreter.symMap.replace("/instrumentation/attitude-indicator/internal-roll-deg",
							new Double(values[6]));
					Interpreter.symMap.replace("/instrumentation/encoder/indicated-altitude-ft", new Double(values[7]));
					Interpreter.symMap.replace("/instrumentation/encoder/pressure-alt-ft", new Double(values[8]));
					Interpreter.symMap.replace("/instrumentation/gps/indicated-altitude-ft", new Double(values[9]));
					Interpreter.symMap.replace("/instrumentation/gps/indicated-ground-speed-kt",
							new Double(values[10]));
					Interpreter.symMap.replace("/instrumentation/gps/indicated-vertical-speed", new Double(values[11]));
					Interpreter.symMap.replace("/instrumentation/heading-indicator/indicated-heading-deg",
							new Double(values[12]));
					Interpreter.symMap.replace("/instrumentation/magnetic-compass/indicated-heading-deg",
							new Double(values[13]));
					Interpreter.symMap.replace("/instrumentation/slip-skid-ball/indicated-slip-skid",
							new Double(values[14]));
					Interpreter.symMap.replace("/instrumentation/turn-indicator/indicated-turn-rate",
							new Double(values[15]));
					Interpreter.symMap.replace("/instrumentation/vertical-speed-indicator/indicated-speed-fpm",
							new Double(values[16]));
					Interpreter.symMap.replace("/controls/flight/aileron", new Double(values[17]));
					Interpreter.symMap.replace("/controls/flight/elevator", new Double(values[18]));
					Interpreter.symMap.replace("/controls/flight/rudder", new Double(values[19]));
					Interpreter.symMap.replace("/controls/flight/flaps", new Double(values[20]));
					Interpreter.symMap.replace("/controls/engines/current-engine/throttle", new Double(values[21]));
					Interpreter.symMap.replace("/engines/engine/rpm", new Double(values[22]));
					Interpreter.symMap.replace("/controls/flight/speedbrake", new Double(values[23]));

					try {

						Thread.sleep(1000 / timeInSec);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				in.close();
				client.close();
			} catch (SocketTimeoutException e) {
			}

			server.close();
		} catch (IOException e) {
		}
	}

	public void start(int port) {
		new Thread(() -> runServer()).start();
	}

}