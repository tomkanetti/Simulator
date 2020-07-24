package test;

public class SleepCommand implements Command{

	int timeToSleep;

	@Override
	public void execute() {
		
		try {
			Thread.sleep(this.timeToSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public int setValues(String[] tokens, int index) {
		this.timeToSleep= Integer.parseInt(tokens[++index]);
		return ++index;
	}
	
	
	
	
	
	
	
	
}
