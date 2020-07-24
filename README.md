package view;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.MyModel;
import viewModel.MyViewModel;


public class viewController implements Observer{

	MyViewModel vm;
	
	@FXML 
	public TextArea txtArea;
	@FXML
	private ToggleGroup checkButtomAM;
	@FXML
	private Circle circle;
	@FXML
	private Circle joystick;
	@FXML
	private RadioButton ManualCB;
	@FXML
	private RadioButton AutoCB;


	public DoubleProperty aileron, elevator, throttle, rudder;
	
	
	 
	 public viewController() {
		 vm= new MyViewModel(new MyModel());
		 aileron= new SimpleDoubleProperty();
		elevator= new SimpleDoubleProperty();
		throttle= new SimpleDoubleProperty();
		rudder= new SimpleDoubleProperty();
		
		vm.aileron.bind(aileron);
		vm.elevator.bind(elevator);
		vm.throttle.bind(throttle);
		vm.rudder.bind(rudder);
			
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

	public void connect() {

		Dialog<connectionData> dialog = new Dialog<connectionData>();
		dialog.setTitle("Set Connection");
		dialog.setHeaderText("Enter IP and Port");
		dialog.setResizable(true);
		Label label1 = new Label("IP: ");
		Label label2 = new Label("Port: ");
		TextField text1 = new TextField();
		TextField text2 = new TextField();
		
		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(text2, 2, 2);
		dialog.getDialogPane().setContent(grid);
		
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		
		dialog.setResultConverter(new Callback<ButtonType, connectionData>() {
		    @Override
		    public connectionData call(ButtonType b) {
		 
		        if (b == buttonTypeOk) {
		 
		            return new connectionData(text1.getText(), text2.getText());
		        }
		        return null;
		    }
		});
		
		//this.setViewModel(new MyViewModel(new MyModel()));
		
		Optional<connectionData> result = dialog.showAndWait();
		if (result.isPresent()) {
		this.vm.connect(result.get().ip, Integer.parseInt(result.get().port));
		
		}
	
	}


	@FXML
	void changeStateOfCircle(MouseEvent event) {
		try {
			System.out.print("Radios " + circle.getRadius());
			System.out.println("X " + joystick.getCenterX() + " Y : " + joystick.getCenterY());
			System.out.println(circle.getRadius());
			double maxBorder = circle.getRadius();
		if(ManualCB.isSelected()){
			if((joystick.getCenterX()<=maxBorder && joystick.getCenterY()<= maxBorder)
					&& (joystick.getCenterX()>= -maxBorder && joystick.getCenterY()>= -maxBorder)) {
				joystick.setCenterX(event.getX());
				joystick.setCenterY(event.getY());
				aileron.setValue((double) 0.1*event.getX()%1);
				elevator.setValue((double) 0.1*event.getY()%1);
				
			}else {
				joystick.setCenterX(0);
				joystick.setCenterY(0);
				return;
			}
			this.vm.sendToSim();
			try {Thread.sleep(40);} catch (InterruptedException e1) {}
			
			
		}
		return;
		} catch (Exception e) {
			System.out.println(e.getMessage()+"!!!!!!!!!!");// TODO: handle exception
		}
	}


	@FXML
	void backJostickToDef(MouseEvent event) {
		joystick.setCenterX(0);
		joystick.setCenterY(0);
	}
	
	
	
	public void calculatePath() {
		final Stage dialog = new Stage();
		
		
		
	}
	
	

	@FXML
	void initialize() {
		assert AutoCB != null : "fx:id=\"AutoCB\" was not injected: check your FXML file 'view.fxml'.";
		assert checkButtomAM != null : "fx:id=\"checkButtomAM\" was not injected: check your FXML file 'view.fxml'.";
		assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'view.fxml'.";
		assert ManualCB != null : "fx:id=\"ManualCB\" was not injected: check your FXML file 'view.fxml'.";
		assert circle != null : "fx:id=\"circly\" was not injected: check your FXML file 'view.fxml'.";
		assert joystick != null : "fx:id=\"jostick\" was not injected: check your FXML file 'view.fxml'.";
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
