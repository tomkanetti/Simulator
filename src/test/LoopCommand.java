package test;

import java.util.LinkedList;

public class LoopCommand implements Command {

	String [] lines= null;
	String [] conditioned = null;
	String var= null;
	String term= null;
	double valueToCheck;
	String temp="";
	
	@Override
	public void execute() {
		lines=this.temp.split(" ");
		//int executeIndex=0;
		
		while (this.itsOk()) { 
			int executeIndex=0; 
			//System.out.println("got into loop");
			while(executeIndex<this.lines.length) {
				CommandExpression e =  Interpreter.expMap.get(lines[executeIndex]);
				if(e!=null) {
					executeIndex = e.setValues(lines, executeIndex);
					e.calculate();
				}
				else ++executeIndex;
			}
		}
	}
	
	@Override
	public int setValues(String[] tokens, int index) {

		int newIndex=index+1;
		String condition=tokens[newIndex]+" "+tokens[newIndex+1]+" "+tokens[newIndex+2];//the condition always 3 words??
		//System.out.println(condition); 
		//newIndex+=3;
		newIndex+=4;
		this.conditioned = condition.split(" ");
		this.var= conditioned[0];
		this.term= conditioned[1];
		this.valueToCheck=  Q3.calc(conditioned[2]);

		while(!tokens[newIndex].equals("}")) {
			this.temp+=tokens[newIndex];
			this.temp+=" ";
			newIndex+=1;
		}	
		
		return ++newIndex; // does it need to be ++ ???? 
	}

	public Boolean itsOk () {	
		switch (this.term) {
		case "<":
			if(Interpreter.isBinded.containsKey(this.var)){
				if (Interpreter.symMap.get(Interpreter.varMap.get(this.var)) < this.valueToCheck)
					return true;
				else return false;
			}
			else {
				Double varValue= new Double(Interpreter.varMap.get(this.var));
				if (varValue < this.valueToCheck)
					return true;
				else return false;
			}
				
		case ">":
			if(Interpreter.isBinded.containsKey(this.var)){
				if (Interpreter.symMap.get(Interpreter.varMap.get(this.var)) > this.valueToCheck)
					return true;
				else return false;
			}
			else {
				Double varValue= new Double(Interpreter.varMap.get(this.var));
				if (varValue > this.valueToCheck)
					return true;
				else return false;
			}
			
		case ">=":
			if(Interpreter.isBinded.containsKey(this.var)){
				if (Interpreter.symMap.get(Interpreter.varMap.get(this.var)) >= this.valueToCheck)
					return true;
				else return false;
			}
			else {
				Double varValue= new Double(Interpreter.varMap.get(this.var));
				if (varValue >= this.valueToCheck)
					return true;
				else return false;
			}
			
		case "<=":
			if(Interpreter.isBinded.containsKey(this.var)){
				if (Interpreter.symMap.get(Interpreter.varMap.get(this.var)) <= this.valueToCheck)
					return true;
				else return false;
			}
			else {
				Double varValue= new Double(Interpreter.varMap.get(this.var));
				if (varValue <= this.valueToCheck)
					return true;
				else return false;
			}
			
		case "==":
			if(Interpreter.isBinded.containsKey(this.var)){
				if (Interpreter.symMap.get(Interpreter.varMap.get(this.var)).equals(this.valueToCheck))
					return true;
				else return false;
			}
			else {
				Double varValue= new Double(Interpreter.varMap.get(this.var));
				if (varValue.equals(this.valueToCheck))
					return true;
				else return false;
			}
			
		case "!=":
			if(Interpreter.isBinded.containsKey(this.var)){
				if (!(Interpreter.symMap.get(Interpreter.varMap.get(this.var)).equals(this.valueToCheck)))
					return true;
				else return false;
			}
			else {
				Double varValue= new Double(Interpreter.varMap.get(this.var));
				if (!(varValue.equals(this.valueToCheck)))
					return true;
				else return false;
			}
			default:
				System.out.println("problem default loop!!");
				return false;
		}

	}
}


