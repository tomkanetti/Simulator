package server_side;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Game implements Searchable<Point> {

	Double[][] matrix;
	Point start;
	Point finish;
	int rows;
	int colums;

	public Game(Double[][] matrix, Point start, Point finish, int rows, int colums) {
		this.matrix = matrix;
		this.start = start;
		this.finish = finish;
		this.rows = rows;
		this.colums = colums;
	}

	@Override
	public State<Point> getInitialState() {
		State<Point> stateInit = new State<Point>(start);
		stateInit.setCost(matrix[start.getX()][start.getY()]);

		return stateInit;
	}

	@Override
	public State<Point> ifGoal() {
		// return (ifE(this.PointToState(finish,null),s));

		State<Point> stateGoal = new State<Point>(finish);
		stateGoal.setCost(matrix[finish.getX()][finish.getY()]);

		return stateGoal;
	}

	public int getX(State<Point> s) {

		return s.getState().x;
	}

	public int getY(State<Point> s) {

		return s.getState().y;
	}
	
	public Boolean ifEq(State<Point> s,State<Point> t ) {
		if(s.getState().x==t.getState().x&&s.getState().y==t.getState().y) 
			return true;
		return false;
	}

	@Override
	public ArrayList<State<Point>> getAllPossibleStates(State<Point> s) {

		ArrayList<State<Point>> arrayPoss = new ArrayList<>();
		int x = s.getState().getX();
		int y = s.getState().getY();
		
		if (x - 1 > 0) {
			Point pointUp = new Point(x - 1, y);
			State<Point> stateUp = new State<Point>(pointUp);
			stateUp.setCost(matrix[x - 1][y] + s.getCost());
			arrayPoss.add(stateUp);

		}
		if (y - 1 >= 0) {
			Point pointLeft = new Point(x, y - 1);
			State<Point> stateLeft = new State<Point>(pointLeft);
			stateLeft.setCost(matrix[x][y - 1] + s.getCost());
			arrayPoss.add(stateLeft);
		}
		//int rows = matrix.length - 1;
		//int cols = matrix[0].length - 1;
		
		
		if (x + 1 < rows) {
			
			Point pointDown = new Point(x + 1, y);
			State<Point> stateDown = new State<Point>(pointDown);
			stateDown.setCost(matrix[x + 1][y] + s.getCost());
			arrayPoss.add(stateDown);
					}
		if (y + 1 < colums) {
			Point pointRight = new Point(x, y + 1);
			State<Point> stateRight = new State<Point>(pointRight);
			stateRight.setCost(matrix[x][y + 1] + s.getCost());
			arrayPoss.add(stateRight);
		}
		return arrayPoss;
	}

	public State<Integer[]> PointToState(Point p, State<Integer[]> papa) {
		State<Integer[]> s = new State<Integer[]>();
		Integer[] i = new Integer[2];
		i[0] = p.x;
		i[1] = p.y;
		s.setState(i);
		// s.setCost(matrix[i[0].intValue()][i[1].intValue()]);
		if (papa != null) {
			s.setCost((this.matrix[p.x][p.y]).doubleValue());
		}
		s.setCost((this.matrix[p.x][p.y]).doubleValue());
		s.setCameFrom(papa);
		return s;
	}

	public boolean ifE(State<Integer[]> s1, State<Integer[]> s2) {
		if ((s1.getState()[0] == s2.getState()[0]) && (s1.getState()[1] == s2.getState()[1]))
			return true;
		else
			return false;
	}
}
