package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.MyModel;
import model.model;
import view.connectionData;

public class MyViewModel extends Observable implements Observer {

	MyModel model;
	Boolean calcPosition = false;
	public DoubleProperty xCanvas, yCanvas, xMatrix, yMatrix,aileron, elevator, throttle, rudder;

	public void sendToSim() {
		this.model.sendToSim(aileron.get(), elevator.get(), throttle.get(), rudder.get());
	}

	public void calcPosition() {
		if (this.calcPosition == false) {
			this.calcPosition = true;
			this.model.calcPosition();
		}

	}

	public void setDest(int[] destination) {
		this.model.setDest(destination);
	}


	public void run(String[] allLines) { 
		this.model.runScript(allLines);
	}

	public MyViewModel(MyModel model) {
		this.model = model;
		aileron = new SimpleDoubleProperty();
		elevator = new SimpleDoubleProperty();
		throttle = new SimpleDoubleProperty();
		rudder = new SimpleDoubleProperty();
		this.xCanvas = new SimpleDoubleProperty();
		this.xMatrix = new SimpleDoubleProperty();
		this.yCanvas = new SimpleDoubleProperty();
		this.yMatrix = new SimpleDoubleProperty();

		this.model.addObserver(this);
	}

	public void openBFSserver(connectionData cd) {
		this.model.openBFSserver(cd);
	}

	public void calculatePath() {
		this.model.calculatePath();

	}

	public void setMap1(Double[][] map) {
		this.model.setMap1(map);

	}

	@Override
	public void update(Observable o, Object arg) {
		MyModel m = (MyModel) o;
		this.xCanvas.set(m.xCanvas);
		this.xMatrix.set(m.xMatrix);
		this.yCanvas.set(m.yCanvas);
		this.yMatrix.set(m.yMatrix);
		setChanged();
		if(this.model.solution!= null)
		notifyObservers(this.model.solution);
		else notifyObservers();
	}

	public void connect(String ip, int port) {
		this.model.connect(ip, port);
	}

}
