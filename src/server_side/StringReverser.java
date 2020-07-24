package server_side;

public class StringReverser implements Solver <String, String> {

	public String solve(String problem) {
	return (new StringBuilder(problem).reverse().toString());
	}
}
