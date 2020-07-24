package test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Q3 {
	private static double varValue;
	
	public static double calc(String exp){
		Queue<String> queue = new LinkedList<String>();
		Stack<String> stack = new Stack<String>();
		Stack<Expression> stackExp = new Stack<Expression>();
		
		String[] split = exp.split("(?<=[-+*/()])|(?=[-+*/()])"); // is it split words either???? 
		for (String s : split){
			if(isVar(s)) {
			    if (Interpreter.isBinded.containsKey(s)) {
			    	double temp= Interpreter.symMap.get(Interpreter.varMap.get(s));
			    	String t = new Double(temp).toString();
			    	queue.add(t);
			    }
			    else {
			    	String t = Interpreter.varMap.get(s); // here is the problem - this dont work good! 
			    	queue.add(t);
			    }
			}
			else if (isDouble(s)){
				queue.add(s);
			}
			else{
				switch(s) {
			    case "/":
			    case "*":
			    case "(":
			        stack.push(s);
			        break;
			    case "+":
			    case "-":
			    	while (!stack.empty() && (!stack.peek().equals("("))){
			    		queue.add(stack.pop());
			    	}
			        stack.push(s);
			        break;
			    case ")":
			    	while (!stack.peek().equals("(")){
			    		queue.add(stack.pop());
			    	}
			    	stack.pop();
			        break;
			}
		}
	}
		while(!stack.isEmpty()){
			queue.add(stack.pop());
		}
		
		for(String str : queue) {

			//if(str==null) break;
			if (isDouble(str)){
				stackExp.push(new Number(Double.parseDouble(str)));
			}
			else{
				Expression right = stackExp.pop();
				Expression left = stackExp.pop();
				
				switch(str) {
			    case "/":
			    	stackExp.push(new Div(left, right));
			        break;
			    case "*":
			    	stackExp.push(new Mul(left, right));
			        break;
			    case "+":
			    	stackExp.push(new Plus(left, right));
			        break;
			    case "-":
			    	stackExp.push(new Minus(left, right));
			        break;
				}
			}
		}

		return Math.floor((stackExp.pop().calculate() * 1000)) /1000;
}

	private static boolean isDouble(String val){
		//if (val == null) return false;
		try {
		    Double.parseDouble(val);
		    return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	
	private static boolean isVar(String s){
		if (Interpreter.varMap.containsKey(s)) return true;
		else return false;
	}

	
	
}
	



