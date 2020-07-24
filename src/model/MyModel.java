package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import server_side.ClientHandler;
import server_side.MyClientHandler;
import server_side.MySerialServer;
import server_side.Server;
import test.Interpreter;
import view.connectionData;
import test.Client;
import test.DateReaderServer;

public class MyModel extends Observable implements model {

	public static Client client, BFSclient;
	public Client PositionClient;
	Map<String, String> JoyStickMap;
	public Interpreter interpreter;
	DateReaderServer server;
	public Server BFSserver;
	int[] source, destination;
	public Thread thread, calcThread;
	public String solution;
	Boolean con, con2;
	Double[][] map;
	String ip;
	int port;
	public Double xFromSim, yFromSim, xCanvas, yCanvas, xMatrix, yMatrix, w, h;

	public MyModel() {
		this.interpreter = new Interpreter();
		this.JoyStickMap = new HashMap<>();
		this.JoyStickMap.put("aileron", "/controls/flight/aileron");
		this.JoyStickMap.put("elevator", "/controls/flight/elevator");
		this.JoyStickMap.put("throttle", "/controls/engines/current-engine/throttle");
		this.JoyStickMap.put("rudder", "/controls/flight/rudder");
		this.server = new DateReaderServer(5400, 10);
		//this.BFSserver = null;
		source = new int[2];
		destination = new int[2];
//		map = null;
//		xFromSim = null;
//		yFromSim = null;
//		xCanvas = null;
//		yCanvas = null;
//		xMatrix = null;
//		yMatrix = null;
//		this.solution = null;
		con = false;
		con2 = false;
	}

	public void setMap1(Double[][] map) {
		this.map = map;

	}

	public void runScript(String[] allLines) {// we need to make the view to notify when needed

		this.thread = new Thread(new Runnable() {
			@Override
			public void run() {
				interpreter.lexer(allLines);
				interpreter.parser();
			}
		});
		this.thread.start();
	}

	public void setDest(int[] destination) {
		this.destination = destination;

	}

	public Double getVal(String line) {
		int i = 0, j = 0;
		char[] temp1 = line.toCharArray();
		for (int k = 0; k < temp1.length; k++) {
			if (temp1[k] == 39) {
				if (i == 0)
					i = k + 1;
				else
					j = k;
			}
		}

		return Double.valueOf(line.substring(i, j));
	}

	void getPosition() {
		this.PositionClient = new Client("127.0.0.1", 5402);

		while (true) {
			try {

				PositionClient.out.println("get /position/latitude-deg");
				PositionClient.out.flush();
				xFromSim = Math.abs(this.getVal(PositionClient.br.readLine()));

				PositionClient.out.println("get /position/longitude-deg");
				PositionClient.out.flush();
				yFromSim = Math.abs(this.getVal(PositionClient.br.readLine()));

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			xCanvas = ((xFromSim - 21.0) * 200);
			yCanvas = ((yFromSim - 157.2) * 200);

			w = (double) 200 / map[0].length; // w length of every cell
			h = (double) 200 / map.length; // h length of every cell

			xMatrix = xCanvas / w;
			yMatrix = yCanvas / h;

			source[0] = (xMatrix).intValue();
			source[1] = (yMatrix).intValue();

			this.setChanged();
			this.notifyObservers();
			try {

				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void run(String[] allLines) {
		this.interpreter.lexer(allLines);
		this.interpreter.parser();

	}

	@Override
	public void connect(String ip, int port) {
		MyModel.client = new Client(ip, port);

	}

	public void openBFSserver(connectionData cd) {
		this.BFSserver = new MySerialServer(5555);
		BFSserver.start(5555, new MyClientHandler());

		if (this.con == false) {
			System.out.println("BFS-server opened");
			con = true;
		}
		this.ip = cd.getIp();
		this.port = Integer.parseInt(cd.getPort());
		calculatePath();
	}

	public void calcPosition() {
		new Thread(() -> getPosition()).start();
	}

	@Override
	public void calculatePath() {
		new Thread(() -> calculatePathSol()).start();
	}

	public void calculatePathSol() {
		MyModel.BFSclient = new Client(ip, port);
		if (this.con2 == false) {
			System.out.println("Client connected");
			con2 = true;
		}
		String s = "";
		for (int i = 0; i < map.length; i++) {
			s = "";
			for (int j = 0; j < map[i].length; j++) {
				s += map[i][j];
				s += ",";
			}
			s = (s.substring(0, s.length() - 1));
			BFSclient.out.println(s);
			BFSclient.out.flush();
		}

		BFSclient.out.println("end");
		BFSclient.out.flush();
		BFSclient.out.println(source[0] + "," + source[1]);
		BFSclient.out.flush();
		BFSclient.out.println(destination[0] + "," + destination[1]);
		BFSclient.out.flush();

		try {

			solution = BFSclient.br.readLine();
			setChanged();
			notifyObservers();
			System.out.println("The Best Path is: " + solution);

			this.BFSserver.stop();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendToSim(Double aileron, Double elevator, Double throttle, Double rudder) {

		client.out.println("set " + this.JoyStickMap.get("aileron") + " " + aileron);
		client.out.println("set " + this.JoyStickMap.get("elevator") + " " + elevator);
		client.out.println("set " + this.JoyStickMap.get("throttle") + " " + throttle);
		client.out.println("set " + this.JoyStickMap.get("rudder") + " " + rudder);
		client.out.flush();

	}

}