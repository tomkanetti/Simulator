package test;

public class BindCommand implements Command {

	String var;
	String simCommand;
	
	@Override
	public void execute() {
	Interpreter.varMap.replace(var, simCommand); 
	if(!Interpreter.isBinded.containsKey(var))
			Interpreter.isBinded.put(var, true);
	}


	@Override
	public int setValues(String[] tokens, int index) {
		int NewIndex = index;
		this.var= tokens[index-1];
		this.simCommand= tokens[NewIndex+2];
		
//		System.out.println("*****");
//		System.out.println("tokens[NewIndex+2]:"+tokens[NewIndex+2]);
//		System.out.println("*****");
//		
//		
//		
//		System.out.println("simbeforesub:"+simCommand);
//		System.out.println("var::"+var);
//		System.out.println("*****");
		
		
		String s =simCommand.substring(1, simCommand.length()-1); // now it should be without ""
		this.simCommand=s;
		
		
//		System.out.println("------");
//		System.out.println("the value of sim command from bind command after sub:");
//		System.out.println(simCommand);  ////////
//		System.out.println("var::"+var);
//		System.out.println("------");
//	
		
		return NewIndex+3;
	}
}


//y = bind simx
