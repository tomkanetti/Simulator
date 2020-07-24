package server_side;

public class SearcherToSolver <T,Solution> implements Solver <Searchable<T>, Solution> {

	Searcher<T,Solution> s;
	
	public SearcherToSolver() {
	this.s=null;
	}

	public SearcherToSolver(Searcher<T, Solution> s) {
		this.s = s;
	}
	

	@Override
	public Solution solve(Searchable<T> problem) {
		return s.search(problem);
	}
}
