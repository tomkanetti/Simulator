package server_side;

public interface Searcher<T,Solution> {
	public Solution search(Searchable<T> s);
	public int getNumberOfNodesEvaluated();
}
