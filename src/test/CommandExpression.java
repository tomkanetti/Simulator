package test;

import java.util.HashMap;

public class CommandExpression implements Expression{

	Command command;
	
	public CommandExpression(Command command) {
		this.command = command;
		
	}

	@Override
	public double calculate() {
		this.command.execute();
		return 0;
	}
	
	public int setValues(String[] tokens, int index) {
		return this.command.setValues(tokens, index);		
	}
}

