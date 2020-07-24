package test;

import java.io.IOException;

import model.MyModel;

public class DisconnectCommand implements Command {

	@Override
	public void execute() {

		//Interpreter.client.Send("bye"); // needed? 
		//Interpreter.client.out.close();		
		MyModel.client.Send("bye"); // needed? 
		//Interpreter.client.out.close();	
		MyModel.client.out.close();
		try {
			//Interpreter.client.client.close();
			MyModel.client.client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}try {Thread.sleep(1000);} catch (InterruptedException e1) {}

		
//		if (Interpreter.server!=null )
//			Interpreter.server.stop();
//	}
	}

	@Override
	public int setValues(String[] tokens, int index) {
		return ++index;
	}
}



