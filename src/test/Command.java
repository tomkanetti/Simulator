package test;

import java.util.HashMap;

public interface Command {

	void execute();
	int setValues(String [] tokens, int index);
	
}
