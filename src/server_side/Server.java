package server_side;

public interface Server {

	//public void runServer() throws Exception;
	public void stop();
	public void start(int port, ClientHandler c);
}
