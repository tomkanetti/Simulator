package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.MyModel;
import test.Interpreter;
import viewModel.MyViewModel;

public class viewController implements Observer {

	MyViewModel vm;

	@FXML
	MapDisplayer mapDisplayer;

	@FXML
	private Circle redLight;
	@FXML
	private Circle greenLight;
	@FXML
	private Button connectButton;
	@FXML
	private Button calculatePathButton;
	@FXML
	private ImageView Ximage;
	@FXML
	private Button loadDataButton;
	@FXML
	private Button loadFileButton;
	@FXML
	private TextArea txtArea;
	@FXML
	private Button runButton;
	// Choice Manual or Auto
	@FXML
	private ToggleGroup checkButtomAM;
	@FXML
	private RadioButton autoCB;
	@FXML
	private RadioButton manualCB;
	// Manual Pilot
	@FXML
	private Circle circle;
	@FXML
	private Circle joystick;
	@FXML
	private Slider rudderSlider;
	@FXML
	private Slider throtlleSilder;
	public DoubleProperty aileron, elevator, throttle, rudder;
	double sceneX, sceneY, translateX, translateY;
	public connectionData connection;
	Double[] source;
	int[] dest;
	Double sizeOfCell;
	Double[][] map;

	public viewController() {

		vm = new MyViewModel(new MyModel());
		this.vm.addObserver(this);
		aileron = new SimpleDoubleProperty();
		elevator = new SimpleDoubleProperty();
		throttle = new SimpleDoubleProperty();
		rudder = new SimpleDoubleProperty();

		vm.aileron.bind(aileron);
		vm.elevator.bind(elevator);
		vm.throttle.bind(throttle);
		vm.rudder.bind(rudder);

		source = new Double[2];
		dest = new int[2];
		map = null;

	}

	@FXML
	void clicked(MouseEvent event) {
		if (this.mapDisplayer.getW() != null) {
			Double x = event.getX();
			Double y = event.getY();
			this.mapDisplayer.clicked(x, y);
			dest[0] = (int) (y / mapDisplayer.getH());
			dest[1] = (int) (x / mapDisplayer.getW());
			this.vm.setDest(dest);
			if (this.mapDisplayer.beingCalc) {
				this.vm.openBFSserver(connection);
				// this.vm.calculatePath();

			}
			this.mapDisplayer.xPath = this.mapDisplayer.xMatrix.get();
			this.mapDisplayer.yPath = this.mapDisplayer.yMatrix.get();
		}

	}

	@FXML
	void allAutoPilotMode(ActionEvent event) {


		if (redLight.getOpacity() == 1) {

			Alert errorAlert = new Alert(AlertType.ERROR); 
			errorAlert.setHeaderText("Error.");
			errorAlert.setContentText("Hey Eli, Try to connect first :-)");
			errorAlert.showAndWait();
			manualCB.setSelected(true);
			calculatePathButton.setDisable(true);
			loadDataButton.setDisable(true);

		} else {
			// Disable all Manual
			joystick.setDisable(true);
			circle.setDisable(true);
			rudderSlider.setDisable(true);
			throtlleSilder.setDisable(true);

			// Up all Auto
			loadFileButton.setDisable(false);
			txtArea.setDisable(false);
			runButton.setDisable(false);
			connectButton.setDisable(false);
			calculatePathButton.setDisable(false);
			loadDataButton.setDisable(false);

			System.out.println("Auto-Pilot mode is On");

		}

	}

	@FXML
	void allManualMode(ActionEvent event) {

		if (redLight.getOpacity() == 1) {
			calculatePathButton.setDisable(true);
			loadDataButton.setDisable(true);

		} else {
			calculatePathButton.setDisable(false);
			loadDataButton.setDisable(false);
		}

		joystick.setDisable(false);
		circle.setDisable(false);
		rudderSlider.setDisable(false);
		throtlleSilder.setDisable(false);
		// Up all Auto
		txtArea.setDisable(true);
		connectButton.setDisable(false);

		loadFileButton.setDisable(true);
		txtArea.setDisable(true);
		runButton.setDisable(true);
		System.out.println("Manual mode is On");
	}

	@FXML
	void changePositionrudderSlider(MouseEvent event) {
		rudder.set(rudderSlider.getValue());
		if (MyModel.client != null)
			this.vm.sendToSim();
	}

	@FXML
	void changePositionthrotlleSilder(MouseEvent event) {
		throttle.set(throtlleSilder.getValue());
		if (MyModel.client != null)
			this.vm.sendToSim();
	}

	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open file");
		fc.setInitialDirectory(new File("./resources"));
		FileChooser.ExtensionFilter exFilter1 = new FileChooser.ExtensionFilter("txt File (*.txt)", "*.txt");
		fc.getExtensionFilters().add(exFilter1);
		File choosen = fc.showOpenDialog(null);
		if (choosen != null) {
			Scanner scanner;
			try {
				scanner = new Scanner(choosen.getAbsoluteFile());
				while (scanner.hasNextLine()) {
					String temp = scanner.nextLine();
					txtArea.appendText(temp);
					txtArea.appendText("\n");
				}
				scanner.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void openCSV() {

		List<List<String>> lines = new ArrayList<>();
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Map File");
		fc.setInitialDirectory(new File("./resources"));
		FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		fc.getExtensionFilters().add(extFilter1);
		File choosen = fc.showOpenDialog(null);
		if (choosen != null) {
			this.mapDisplayer.beingCalc = false;
			this.mapDisplayer.xCanvas = new SimpleDoubleProperty();
			this.mapDisplayer.xMatrix = new SimpleDoubleProperty();
			this.mapDisplayer.yCanvas = new SimpleDoubleProperty();
			this.mapDisplayer.yMatrix = new SimpleDoubleProperty();

			mapDisplayer.xCanvas.bind(this.vm.xCanvas);
			mapDisplayer.xMatrix.bind(this.vm.xMatrix);
			mapDisplayer.yCanvas.bind(this.vm.yCanvas);
			mapDisplayer.yMatrix.bind(this.vm.yMatrix);

			try (Scanner scanner = new Scanner(choosen)) {
				while (scanner.hasNextLine()) {
					lines.add(getRecordFromLine(scanner.nextLine()));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		source[0] = Double.valueOf(lines.get(0).get(0));
		source[1] = Double.valueOf(lines.get(0).get(1));
		sizeOfCell = Double.valueOf(lines.get(1).get(0));

		int i = 0;
		int k = 0;
		int j = 0;

		Double[][] temp = new Double[lines.size() - 2][lines.get(2).size()];

		for (i = 2; i < lines.size(); i++) {
			for (j = 0; j < lines.get(2).size(); j++) {

				temp[i - 2][j] = Double.parseDouble(lines.get(i).get(j));
			}
		}

		map = temp;
		this.vm.setMap1(temp);
		this.mapDisplayer.setMazeData(temp);
		this.vm.calcPosition();
		calculatePathButton.setDisable(false);

	}

	private List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
		}
		return values;
	}

	public void connect() {

		connectionData cd = showConnectDialog("127.0.0.1", "5402");
		this.vm.connect(cd.ip, Integer.parseInt(cd.port));
		greenLight.opacityProperty().set(1);
		redLight.opacityProperty().set(0);

		if (autoCB.isSelected()) {
			loadFileButton.setDisable(false);
			txtArea.setDisable(false);
			runButton.setDisable(false);
			txtArea.setDisable(false);
		}
		loadDataButton.setDisable(false);
		System.out.println("Connected To FlightGear Simulator");
	}

	@FXML
	void changeStateOfCircle(MouseEvent event) {
		try {

			Double m = event.getY() / event.getX(); 
			Double r = this.circle.getRadius();
			Double x = event.getX();
			Double y = event.getY();

			double maxBorder = circle.getRadius();
			if (manualCB.isSelected()) {
				if ((event.getX() <= maxBorder && event.getY() <= maxBorder)
						&& (event.getX() >= -maxBorder && event.getY() >= -maxBorder)) {
					joystick.setCenterX(event.getX());
					joystick.setCenterY(event.getY());
					aileron.setValue(0.01 * (double) joystick.getCenterX() % r); 
					elevator.setValue((-1 * 0.01) * (double) joystick.getCenterY() % r); 

				} else {

					Double newX = Math.sqrt((r * r) / ((m * m) + 1));
					Double newY = m * newX;

					if (x < 0) {
						newX = newX * (-1);
						newY = newY * (-1);
					}

					joystick.setCenterX(newX);
					aileron.setValue(0.01 * (double) joystick.getCenterX() % r); // new !!
					joystick.setCenterY(newY);
					elevator.setValue((-1 * 0.01) * (double) joystick.getCenterY() % r); // new !!

				}

				if (MyModel.client != null)
					this.vm.sendToSim();
			}
			return;
		} catch (Exception e) {
			e.getMessage();// TODO: handle exception
		}
	}

	@FXML
	void backJostickToDef(MouseEvent event) {
		joystick.setCenterX(0);
		joystick.setCenterY(0);
		aileron.setValue(0);
		elevator.setValue(0);
		if (MyModel.client != null)
			this.vm.sendToSim();
	}

	public void calculatePath() { 
		if (this.mapDisplayer.beingCalc == false) {
			connectionData cd = showConnectDialog("127.0.0.1", "5555");
			connection = cd;
			this.mapDisplayer.beingCalc = true;
			this.vm.openBFSserver(cd);

		} else {
			Alert errorAlert = new Alert(AlertType.INFORMATION);
			errorAlert.setHeaderText("!");
			errorAlert.setContentText("Your path is already being calculated");
			errorAlert.showAndWait();
			manualCB.setSelected(true);

		}
	}

	connectionData showConnectDialog(String txt1, String txt2) {
		Dialog<connectionData> dialog = new Dialog<connectionData>();
		dialog.setTitle("Set Connection");
		dialog.setHeaderText("Enter IP and Port");

		dialog.setResizable(true);
		Label label1 = new Label("IP:");
		Label label2 = new Label("Port: ");
		TextField text1 = new TextField(txt1);
		TextField text2 = new TextField(txt2);

		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(text2, 2, 2);
		dialog.getDialogPane().setContent(grid);

		ButtonType buttonTypeOk = new ButtonType("Done", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		dialog.setResultConverter(new Callback<ButtonType, connectionData>() {
			@Override
			public connectionData call(ButtonType b) {

				if (b == buttonTypeOk) {

					if (!(text1.getText().equals("127.0.0.1"))) {

						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Error.");
						errorAlert.setContentText("Wrong IP.");
						errorAlert.showAndWait();
						dialog.showAndWait();
					}

					if (65536 < (Integer.parseInt(text2.getText())) || 0 > (Integer.parseInt(text2.getText()))) {

						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Error.");
						errorAlert.setContentText("Wrong Port.");
						errorAlert.showAndWait();
						dialog.showAndWait();
					}

					return new connectionData(text1.getText(), text2.getText());
				}
				return null;
			}
		});

		Optional<connectionData> result = dialog.showAndWait();

		if (result.isPresent()) {
			return result.get();
		}
		return null;

	}

	public void run() {

		ArrayList<String> lines = new ArrayList<>(Arrays.asList(txtArea.getText().split("\\n")));

		String[] allLines = new String[lines.size()];
		int i = 0;
		for (String s : lines) {
			allLines[i] = s;
			i++;
		}
		this.vm.run(allLines);
	}

	@FXML
	void initialize() {
		assert connectButton != null : "fx:id=\"connectButton\" was not injected: check your FXML file 'view.fxml'.";
		assert calculatePathButton != null : "fx:id=\"calculatePathButton\" was not injected: check your FXML file 'view.fxml'.";
		assert loadDataButton != null : "fx:id=\"loadDataButton\" was not injected: check your FXML file 'view.fxml'.";
		assert loadFileButton != null : "fx:id=\"loadFileButton\" was not injected: check your FXML file 'view.fxml'.";
		assert autoCB != null : "fx:id=\"autoCB\" was not injected: check your FXML file 'view.fxml'.";
		assert checkButtomAM != null : "fx:id=\"checkButtomAM\" was not injected: check your FXML file 'view.fxml'.";
		assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'view.fxml'.";
		assert runButton != null : "fx:id=\"runButton\" was not injected: check your FXML file 'view.fxml'.";
		assert manualCB != null : "fx:id=\"manualCB\" was not injected: check your FXML file 'view.fxml'.";
		assert circle != null : "fx:id=\"circle\" was not injected: check your FXML file 'view.fxml'.";
		assert joystick != null : "fx:id=\"joystick\" was not injected: check your FXML file 'view.fxml'.";
		assert rudderSlider != null : "fx:id=\"rudderSlider\" was not injected: check your FXML file 'view.fxml'.";
		assert throtlleSilder != null : "fx:id=\"throtlleSilder\" was not injected: check your FXML file 'view.fxml'.";

		// Up the system and Run Manual Mode
		allManualMode(null);
	}

	@Override
	public void update(Observable o, Object arg) {

		String s = (String) arg;
		if (s != null)
			this.mapDisplayer.sol = s;
		this.mapDisplayer.drawAll();

	}
}
