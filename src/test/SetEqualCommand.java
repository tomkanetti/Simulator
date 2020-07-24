package test;

import model.MyModel;

public class SetEqualCommand implements Command {  

	 String var;
	 double value;
		 

	public SetEqualCommand() {
		this.var="";
		this.value=0.0;
	}

	@Override
	public void execute() {
		
//		if (Interpreter.server==null) {
//			if (Interpreter.isBinded.containsKey(var))
//				Interpreter.symMap.replace(Interpreter.varMap.get(var), value);
//			else {
//				Double temp = new Double(value);
//				if (Interpreter.varMap.containsKey(var)) {
//					Interpreter.varMap.remove(var);
//					Interpreter.varMap.put(var, temp.toString());
//				}
//				
//				else Interpreter.varMap.put(var, temp.toString());
//				}
//			}
		
		//else {
		if (Interpreter.isBinded.containsKey(var)){

			String s= "set "+Interpreter.varMap.get(var)+" "+value;
			//Interpreter.client.Send(s);
			//System.out.println(s);
			MyModel.client.Send(s);
		}
			
		
		else {
			Double temp = new Double(value);
			//System.out.println(temp);
			if (Interpreter.varMap.containsKey(var)) {
				Interpreter.varMap.remove(var);
				Interpreter.varMap.put(var, temp.toString());
			}
			
			else Interpreter.varMap.put(var, temp.toString());
		}
		
	}
//}

	@Override
	public int setValues(String[] tokens, int index) {
		int NewIndex=index;
		char [] temp = tokens[index-1].toCharArray();
		String s="";
		for ( char c : temp) {
			if( c!='\t') {
				s+=c;
			}
		}
		this.var= s;
		//System.out.println(tokens[NewIndex+1]);
		this.value= Q3.calc(tokens[++NewIndex]);
		
		return ++NewIndex;
	}

}
