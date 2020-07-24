package test;

public class EqualCommand implements Command {
	
	Command c;
	
	@Override
	public void execute() {
		c.execute();
	}
	
	@Override
	public int setValues(String[] tokens, int index) {  //new added
		int newIndex=index;
		if (tokens[index+1].equals("bind")) 
			this.c= new BindCommand();
		else {this.c= new SetEqualCommand();}
		return c.setValues(tokens, newIndex);
	}
}
