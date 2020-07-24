package server_side;

import java.util.HashMap;

public class FileCacheManager <Problem, Solution> implements CacheManager<Problem, Solution> {
	
	HashMap<Problem, Solution> map;
	
	public FileCacheManager(){
	map = new HashMap<Problem, Solution>();
	}

	public void save(Problem problem, Solution solution) {
		map.put(problem, solution);
	}
	
	public FileCacheManager(HashMap<Problem, Solution> map) {
		super();
		this.map = map;
	}

	public Solution find(Problem problem) {
		return map.get(problem);
	}

	public boolean ifExist(Problem problem) {
		return (map.containsKey(problem));
	}

	
	
}
