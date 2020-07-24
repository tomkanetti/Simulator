//package test;     //old
//
//public class DefineVarCommand implements Command {
//	String var;
//	
//	@Override
//	public void execute() {
//		Interpreter.symbolMap.put(var, 0.0);
//}
//
//	@Override
//	public int setValues(String[] tokens, int index) {
//		int newIndex=index;
//		this.var = tokens[++newIndex];
//				return ++newIndex;
//	}
//
//}

package test;

public class DefineVarCommand implements Command {
	String var;
	
	@Override
	public void execute() {
		Interpreter.varMap.put(var,""); 
}

	@Override
	public int setValues(String[] tokens, int index) {
		int newIndex=index;
		this.var = tokens[++newIndex];
				return ++newIndex;
	}

}
