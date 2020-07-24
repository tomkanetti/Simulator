package server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {
private int port;
private ClientHandler ch;
private volatile boolean stop;

	public MySerialServer(int port) {
	super();
	this.port = port;
	this.ch = null;
	this.stop = false;
}
	
	public void stop() {
		stop = true;
	}
	
	public void runServer() throws Exception{
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(1000);
		while (!stop)
		{
			try { Socket aClient = server.accept(); 
				try {
					ch.HandleClient(aClient.getInputStream(), aClient.getOutputStream());
				//	aClient.getInputStream().close();
				//	aClient.getOutputStream().close();
					aClient.close();
					} catch(IOException e) {}
			} catch ( SocketTimeoutException e){}
		}
		server.close();
	}

	public void start(int port, ClientHandler c) {

		this.port = port;
		this.ch = c;
		this.stop = false;
		new Thread (()-> {
			try {
				runServer();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		
	}
}



