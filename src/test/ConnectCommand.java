package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import model.MyModel;

public class ConnectCommand implements Command {

	int port;
	String IP;
	Boolean stop;
	
	public ConnectCommand() {
		this.port=0;
		this.IP=null;
		this.stop=false;
	}
	
	@Override
	public void execute() {
		//Interpreter.client= new Client (IP, port);
		MyModel.client= new Client (IP, port);

	}

	@Override
	public int setValues(String[] tokens, int index) {
		int newIndex = index;
		this.IP= tokens[++newIndex];
		this.port= (int)Q3.calc(tokens[++newIndex]);
		return ++newIndex;
	}
	
	public void close() {
		stop=true;
	}
}

