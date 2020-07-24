package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class MyClientHandler implements ClientHandler {

	public Solver<Searchable<Point>, ArrayList<State<Point>>> solver;
	public CacheManager<Searchable<Point>, String> cm;

	public MyClientHandler(Solver<Searchable<Point>, ArrayList<State<Point>>> solver,
			CacheManager<Searchable<Point>, String> cm) {
		this.solver = solver;
		this.cm = cm;
	}

	public MyClientHandler() {
		this.solver = new SearcherToSolver<>(new BestFS<Point>());
		this.cm = new FileCacheManager<Searchable<Point>, String>();
	}

	public void HandleClient(InputStream in, OutputStream out) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		PrintWriter writer = new PrintWriter(out);
		String line;

		String stringRead = "";
		String[] str = null;
		String[] tmp = null;
		int rows = 0;

		try {
			while (!(line = reader.readLine()).equals("end")) {
				stringRead += line;
				stringRead += "-";
				rows++;
			}

			str = stringRead.split("-");
			tmp = str[0].split(",");
			int colums = tmp.length;

			Double[][] matrix = new Double[rows][colums];

			for (int j = 0; j < colums; j++) {
				matrix[0][j] = Double.parseDouble(tmp[j]);
			}

			for (int i = 1; i < rows; i++) {
				tmp = str[i].split(",");
				for (int j = 0; j < tmp.length; j++) {
					matrix[i][j] = Double.parseDouble(tmp[j]);
				}
			}

			line = reader.readLine();

			
			tmp = line.split(",");
			Point start = new Point(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
			line = reader.readLine();

			
			tmp = line.split(",");
			Point finish = new Point(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
			Game newGame = new Game(matrix, start, finish, rows, colums);////// check if the pitaron is exist
			ArrayList<State<Point>> solved = new ArrayList<State<Point>>();

			solved = solver.solve(newGame);

//			ListIterator<State<Point>> it1 = solved.listIterator();
//			ListIterator<State<Point>> it2 = solved.listIterator();
//			State<Point> d1 = it1.next();
//			State<Point> d2 = it2.next();
//			d2 = it2.next();
			String solution ="";
			int x=start.getX();
			int y=start.getY();

			
			for (int i = 1; i < solved.size(); i++) {
				if((solved.get(i).getState().getX()==x+1)&&(solved.get(i).getState().getY()==y))
				{
					solution+="Down";
					x=x+1;
				}
				else if ((solved.get(i).getState().getX()==x-1)&&(solved.get(i).getState().getY()==y))
				{
					solution+="Up";
					x=x-1;
				}
				else if ((solved.get(i).getState().getX()==x)&&(solved.get(i).getState().getY()==y+1))
				{
					solution+="Right";
					y=y+1;
				}
				else if ((solved.get(i).getState().getX()==x)&&(solved.get(i).getState().getY()==y-1))
				{
					solution+="Left";
					y=y-1;
				}
				if(i<solved.size() - 1)
					solution+=",";
			
			}
			
			
			writer.println(solution);
			writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.close();
	}

}
