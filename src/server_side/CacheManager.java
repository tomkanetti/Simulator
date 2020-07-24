package server_side;

public abstract interface CacheManager <Problem, Solution> {

	public void save (Problem problem, Solution solution);
	public Solution find (Problem problem);
	public boolean ifExist (Problem problem);
}

//public static void main(String[] args) {
//MySerialServer mss= new MySerialServer(Integer.parseInt(args[0]));
//		mss.start(new MyTestClientHandler (new StringRevers,new FileCacheManager));