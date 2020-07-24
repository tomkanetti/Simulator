package model;


import view.connectionData;

public interface model {

	void connect (String ip, int port);

	void sendToSim(Double aileron,Double elevator,Double throttle,Double rudder);

	public void run(String [] allLines);
	
	public void calculatePath();
	
	
}