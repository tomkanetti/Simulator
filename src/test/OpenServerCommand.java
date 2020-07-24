package test;

import java.util.HashMap;

public class OpenServerCommand implements Command {

	int port;
	int timeInSec;
	
	public OpenServerCommand() {
		this.port=0;
		this.timeInSec=0;
	}

	@Override
	public void execute() {
		//Interpreter.server= new DateReaderServer(port,timeInSec);
		//Interpreter.server.start(this.port);

		// open a new server using this.port and this.num.....
	}

	@Override
	public int setValues(String[] tokens, int index) {
		int newIndex = index;
		this.port= (int)Q3.calc(tokens[++newIndex]);
		this.timeInSec= (int)Q3.calc(tokens[++newIndex]);
		
		return ++newIndex;
	}

}
