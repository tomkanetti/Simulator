package server_side;

public class State <T> implements Comparable<State<T>> {
	private T state;
	private double cost;
	private State <T> cameFrom;
	
	public State(T state) {
		this.state = state;
	}
	
	public State() {
		this.setCameFrom(null);
		this.setCost(0);
		this.setState(null);
	}

	public State(T state, double cost, State<T> cameFrom) {
		super();
		this.state = state;
		this.cost = cost;
		this.cameFrom = cameFrom;
	}
	
	public State(State<T> newState,  State<T> cameFrom) {
		super();
		this.state = newState.state;
		this.cost = newState.cost;
		this.cameFrom = cameFrom;
	}

	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}


	public State <T> getCameFrom() {
		return cameFrom;
	}


	public void setCameFrom (State <T> cameFrom) {
		this.cameFrom = cameFrom;
	}


	public boolean equals (State <T> s)
	{
		return this.state.equals(s.state);
	}
	
	
	@Override
	public int compareTo(State<T> s) {
		return (int) (this.cost - s.getCost());
	}
	
}
