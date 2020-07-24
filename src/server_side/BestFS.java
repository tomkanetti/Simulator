package server_side;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

public class BestFS<T> extends CommonSearcher<T, ArrayList<State<T>>> {

	@Override
	public ArrayList<State<T>> search(Searchable<T> s) {
		System.out.println("Calculate Path Using BFS...");

		openList.add(s.getInitialState());

		while (!openList.isEmpty()) {

			State<T> n = openList.remove();

			closedSet.add(n);

			if (s.ifEq(n, s.ifGoal())) {

				return this.backTrace(s.getInitialState(), n, s);
			}
			ArrayList<State<T>> successors = s.getAllPossibleStates(n);
			ArrayList<State<T>> removeList = new ArrayList<>();

			for (State<T> successorState : successors) {

				if (s.ifEq(successorState, s.ifGoal())) {
					successorState.setCameFrom(n);
					return this.backTrace(s.getInitialState(), successorState, s);
				}
				Boolean inOpenList = false;

				for (State<T> t : openList) {

					if (s.ifEq(t, successorState)) {
						if (t.getCost() > successorState.getCost()) {
							removeList.add(t);

							successorState.setCameFrom(n);
							openList.add(successorState);
						}
						inOpenList = true;
					}
					openList.removeAll(removeList);

				}
				for (State<T> z : closedSet) {

					if ((!(s.ifEq(z, successorState)) && !(inOpenList))) {
						successorState.setCameFrom(n);
						openList.add(successorState);

					}

				}

			}

		}

		return null;

	}

	public ArrayList<State<T>> backTrace(State<T> init, State<T> goal, Searchable<T> s) {
		ArrayList<State<T>> stp = new ArrayList<>();

		while (!s.ifEq(goal, init)) {

			stp.add(goal);

			goal = goal.getCameFrom();
		}
		stp.add(init);

		Collections.reverse(stp);

		return stp;
	}

}