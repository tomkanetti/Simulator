

package test;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class Interpreter {
	public static String[] tokens;
	public static HashMap<String, CommandExpression> expMap;
	public static HashMap<String, Double> symMap;
	public static HashMap<String, String> varMap;
	public static HashMap<String, Boolean> isBinded;
	// public static DateReaderServer server;
	// public static Client client;
	public static double returned;

	public Interpreter() {
		tokens = null;
		expMap = new HashMap<>();
		symMap = new HashMap<>();
		varMap = new HashMap<>();
		isBinded = new HashMap<>();
		// server=null;

		expMap.put("openDataServer", new CommandExpression(new OpenServerCommand()));
		expMap.put("connect", new CommandExpression(new ConnectCommand()));
		expMap.put("var", new CommandExpression(new DefineVarCommand()));
		expMap.put("bind", new CommandExpression(new BindCommand()));
		expMap.put("return", new CommandExpression(new ReturnCommand()));
		expMap.put("disconnect", new CommandExpression(new DisconnectCommand()));
		expMap.put("=", new CommandExpression(new EqualCommand()));
		expMap.put("while", new CommandExpression(new LoopCommand()));
		expMap.put("sleep", new CommandExpression(new SleepCommand()));

		symMap.put("/instrumentation/airspeed-indicator/indicated-speed-kt", 0.0);
		symMap.put("/instrumentation/altimeter/indicated-altitude-ft", 0.0);
		symMap.put("/instrumentation/altimeter/pressure-alt-ft", 0.0);
		symMap.put("/instrumentation/attitude-indicator/indicated-pitch-deg", 0.0);
		symMap.put("/instrumentation/attitude-indicator/indicated-roll-deg", 0.0);
		symMap.put("/instrumentation/attitude-indicator/internal-pitch-deg", 0.0);
		symMap.put("/instrumentation/attitude-indicator/internal-roll-deg", 0.0);
		symMap.put("/instrumentation/encoder/indicated-altitude-ft", 0.0);
		symMap.put("/instrumentation/encoder/pressure-alt-ft", 0.0);
		symMap.put("/instrumentation/gps/indicated-altitude-ft", 0.0);
		symMap.put("/instrumentation/gps/indicated-ground-speed-kt", 0.0);
		symMap.put("/instrumentation/gps/indicated-vertical-speed", 0.0);
		symMap.put("/instrumentation/heading-indicator/indicated-heading-deg", 0.0);
		symMap.put("/instrumentation/magnetic-compass/indicated-heading-deg", 0.0);
		symMap.put("/instrumentation/slip-skid-ball/indicated-slip-skid", 0.0);
		symMap.put("/instrumentation/turn-indicator/indicated-turn-rate", 0.0);
		symMap.put("/instrumentation/vertical-speed-indicator/indicated-speed-fpm", 0.0);
		symMap.put("/controls/flight/aileron", 0.0);
		symMap.put("/controls/flight/elevator", 0.0);
		symMap.put("/controls/flight/rudder", 0.0);
		symMap.put("/controls/flight/flaps", 0.0);
		symMap.put("/controls/engines/current-engine/throttle", 0.0);
		symMap.put("/engines/engine/rpm", 0.0);
		symMap.put("/controls/flight/speedbrake", 0.0);

	}

	public void lexer(String[] lines) {

		String allText = "";
		String returnFromFunc = null;
		String[] temp = null;

		for (String line : lines) {
			temp = line.split(" ");
			returnFromFunc = help(temp);
			allText += returnFromFunc;
			// allText+=" ";
		}

		tokens = allText.split(" ");

	}

	public double parser() {
		int index = 0;
		while (index < tokens.length) {
			CommandExpression e = expMap.get(tokens[index]);
			if (e != null) {
				index = e.setValues(tokens, index);
				e.calculate(); // ......
				//////
				try {
					Thread.sleep(200);
				} catch (InterruptedException d) {
					// TODO Auto-generated catch block
					d.printStackTrace();
				}
				//////
			} else
				++index;
		}

		return returned;
	}

	public static String help(String[] s) {
		String[] temp = new String[4000];
		String finalLine = "";
		String correctBefore = "";
		String correctLast = "";
		String equal = "=";
		int flag = 0;
		int i = 1;
		temp[0] = " ";

		for (String str : s) {
			// System.out.println(str);
			// if (str == null)continue;
			if (str.charAt(0) == '"') {
				temp[i] = str;
				i++;
				continue;
			}

			if (isEqual(str)) {
				char[] temp1 = str.toCharArray();
				for (int j = 0; j < temp1.length; j++) {
					if (temp1[j] == '=') {
						for (int k = 0; k < j; k++) {
							if (temp1[k] != '\t') {
								correctBefore += temp1[k];
							}
						}
						j++;
						for (; j < temp1.length; j++) {
							if (temp1[j] != '\t') {
								correctLast += temp1[j];
							}
						}
					}
				}
				temp[i] = correctBefore;
				temp[i + 1] = equal;
				temp[i + 2] = correctLast;
				i += 3;
				continue; // ?????
			}

			if (str.charAt(0) == ')') {
				temp[i - 1] += str;
				continue;
			}
//////
			if (str.charAt(0) == '-') {
				if (temp[i - 1].charAt(temp[i - 1].length() - 1) == '=') {
					String s1 = "0";
					s1 += str;
					temp[i] = s1;
					i++;
					continue;

				} else if (temp[i - 1].charAt(temp[i - 1].length() - 1) == '-'
						|| temp[i - 1].charAt(temp[i - 1].length() - 1) == '*'
						|| temp[i - 1].charAt(temp[i - 1].length() - 1) == '('
						|| temp[i - 1].charAt(temp[i - 1].length() - 1) == '/'
						|| temp[i - 1].charAt(temp[i - 1].length() - 1) == '+') {
					String s1 = "0";
					s1 += str;
					temp[i - 1] += s1;
					continue;
				}
			}
//////
			if (str.charAt(0) == '+' || str.charAt(0) == '-' || str.charAt(0) == '*' || str.charAt(0) == '/') {
				if (temp[i - 1].charAt(0) == '=') { 
					temp[i] = str;
					i++;
					continue;
				}

				else
					temp[i - 1] += str; 
				continue;
			}

			if (temp[i - 1].charAt(temp[i - 1].length() - 1) == '+'
					|| temp[i - 1].charAt(temp[i - 1].length() - 1) == '-'
					|| temp[i - 1].charAt(temp[i - 1].length() - 1) == '*'
					|| temp[i - 1].charAt(temp[i - 1].length() - 1) == '('
					|| temp[i - 1].charAt(temp[i - 1].length() - 1) == '/') {
				temp[i - 1] += str;
				continue;
			}

			else {
				temp[i] = str;
				i++;
				continue;
			}
		}

		temp[i] = "ending";

//		
//		for ( String g : temp) {
//			if (g.equals("ending")) {
//				break;
//			}
//			finalLine += g;
//			finalLine += " ";
//		}

		int numOfStrings = 0;
		int t = 0;
		while (!temp[t].equals("ending")) {
			numOfStrings++;
			t++;
		}

		String[] lastString = new String[numOfStrings];

		for (int y = 0; y < numOfStrings; y++) {
			lastString[y] = temp[y];
		}

		for (int y = 0; y < numOfStrings; y++) {
			finalLine += lastString[y];
			finalLine += " ";
		}

		return finalLine;
	}

	private static boolean isEqual(String str) {
		if (str.equals("="))
			return false; // if "=" is the only letter so its ok
		char[] temp1 = str.toCharArray();
		for (int j = 0; j < temp1.length; j++) {
			if (temp1[j] == '=')
				return true;
		}
		return false;
	}

	private static boolean isMinus(String str) {
		if (str.equals("-"))
			return true; // if "=" is the only letter so its ok
		char[] temp1 = str.toCharArray();
		for (int j = 0; j < temp1.length; j++) {
			if (temp1[j] == '-')
				return true;
		}
		return false;
	}
}
