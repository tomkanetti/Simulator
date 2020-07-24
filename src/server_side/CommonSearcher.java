package server_side;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public abstract class CommonSearcher <T, Solution> implements Searcher<T, Solution> {

	protected PriorityQueue <State<T>> openList;
	private int evaluatedNodes;
	protected Set<State<T>> closedSet;
	
	
	
	 
	public CommonSearcher() 
		{ 
			openList=new PriorityQueue<State<T>>(); 
			evaluatedNodes=0;
			closedSet = new HashSet<>();
			
		}
	
	protected State <T> popOpenList() 
		{ evaluatedNodes++;
		return openList.remove();
		}
	
	@Override
	public int getNumberOfNodesEvaluated() 
		{
		return evaluatedNodes;
		}

	public abstract Solution search(Searchable <T> s);
}
