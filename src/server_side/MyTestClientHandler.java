package server_side;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MyTestClientHandler  implements ClientHandler {

	//public Solver <String, String> solver;
	//public CacheManager <String, String>cm;
	public Solver <String,String> solver;
	public CacheManager <String,String> cm;
	
	public MyTestClientHandler(Solver<String, String> solver, CacheManager<String, String> cm) {
		this.solver = solver;
		this.cm = cm;
	}


	public void HandleClient(InputStream in, OutputStream out)  {
		BufferedReader reader= new BufferedReader(new InputStreamReader(in));
		PrintWriter writer=new PrintWriter(new OutputStreamWriter(out));
		String line;
		try {
			while(!(line = reader.readLine()).equals("end")) {
				
				if ( cm.ifExist(line)) {
					writer.println(cm.find(line));
					
				}
				else {
					cm.save(line, solver.solve(line));
					writer.println(cm.find(line));
					}
				writer.flush();
			}
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