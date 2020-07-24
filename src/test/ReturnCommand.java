package test;

public class ReturnCommand implements Command {

	double value;

	public ReturnCommand() {
		this.value=0.0;
	}

	@Override
	public void execute() {
		Interpreter.returned=value;
	}

	@Override
	public int setValues(String[] tokens, int index) {
		int NewIndex= index;
		this.value=Q3.calc(tokens[++NewIndex]);
		return ++NewIndex;
	}

}
