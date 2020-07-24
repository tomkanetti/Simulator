package server_side;

import java.util.ArrayList;
import java.util.Set;

public interface Searchable <T>{
	public State <T> getInitialState();
	public  State <T> ifGoal();
	ArrayList <State<T>> getAllPossibleStates(State <T> s);
	public int getY(State<T> y);
	public int getX(State<T> x);
	public Boolean ifEq(State<T> s,State<T> t );
	
	
}
