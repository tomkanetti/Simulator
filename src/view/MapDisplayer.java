package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MapDisplayer extends Canvas {

	Double[][] mapData;
	StringProperty xAddress, circleAddr, PlaneAddress;
	Image xImage, aircraftImage, circle;
	Boolean clicked = false;
	Boolean beingCalc = false;
	public String sol;
	public Double xPath, yPath, max, min, w, h, xx, xy;
	public DoubleProperty xCanvas, yCanvas, xMatrix, yMatrix;

	public String getcircleAddr() {
		return circleAddr.get();
	}

	public void setcircleAddr(String s) {
		this.circleAddr.set(s);
	}

	public String getPlaneAddress() {
		return PlaneAddress.get();
	}

	public void setPlaneAddress(String s) {
		this.PlaneAddress.set(s);
	}

	public String getxAddress() {
		return xAddress.get();
	}

	public void setxAddress(String s) {
		this.xAddress.set(s);
	}

	public Double getW() {
		return w;
	}

	public Double getH() {
		return h;
	}

	public void setMazeData(Double[][] mapData) {
		this.mapData = mapData;
		findValues();
		// drawAll();
	}

	public MapDisplayer() {
		max = 0.0;
		min = 0.0;
		mapData = null;
		w = null;
		h = null;
		sol = null;
		xPath = null;
		yPath = null;
		this.xAddress = new SimpleStringProperty();
		this.xAddress.set("./resources/xx.png");
		this.PlaneAddress = new SimpleStringProperty();
		this.PlaneAddress.set("./resources/air.png");
		this.circleAddr = new SimpleStringProperty();
		this.circleAddr.set("./resources/circle.png");

		try {
			this.xImage = (new Image(new FileInputStream(xAddress.get())));
			this.aircraftImage = (new Image(new FileInputStream(PlaneAddress.get())));
			this.circle = (new Image(new FileInputStream(circleAddr.get())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void findValues() {

		for (int i = 0; i < this.mapData.length; i++) { // run on h
			for (int j = 0; j < this.mapData[i].length; j++) { // run on w
				if (mapData[i][j] > max)
					max = mapData[i][j];
				if (mapData[i][j] < min)
					min = mapData[i][j];
			}
		}
	}

	void drawImage(Image image, double x, double y, double sizex, double sizey) {
		GraphicsContext gc = getGraphicsContext2D();
		gc.drawImage(image, x, y, sizex, sizey);
	}

	void drawPath() {
		if (this.sol != null) {

			double newx = this.yPath * w;
			double newy = this.xPath * h;
			String[] solution = this.sol.split(",");
			GraphicsContext gc = getGraphicsContext2D();

			for (String s : solution) {

				if (s.equals("Up")) {

					newy = newy - h;

				} else if (s.equals("Down")) {

					newy = newy + h;

				} else if (s.equals("Left")) {
					newx = newx - w;

				} else if (s.equals("Right")) {
					newx = newx + w;

				}
				this.drawImage(circle, newx - 15, newy, 15, 15);

			}

		}
	}

	void drawAll() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
		redraw();
		if (clicked)
			drawImage(this.xImage, xx - 10, xy - 10, 30, 30);
		drawImage(this.aircraftImage, this.yCanvas.get() - 15, this.xCanvas.get() - 20, 40, 40);
		drawPath();

	}

	void clicked(double x, double y) {

		if (w != null) { // print x
			xx = x;
			xy = y;
			if (!clicked) {
				clicked = true;
			} else {
				GraphicsContext gc = getGraphicsContext2D();
				gc.clearRect(0, 0, getWidth(), getHeight()); // ?
			}
			drawAll();
		}

	}

	public void redraw() {

		GraphicsContext g = getGraphicsContext2D();
		g.setFill(Color.rgb(0, 83, 0));
		g.fillRect(0, 0, 200, 200);

		if (this.mapData != null) {
			Double mapW = this.getWidth();
			Double mapH = this.getHeight();

			w = mapW / mapData[0].length; // w length of every ta
			h = mapH / mapData.length; // h length of every ta

			GraphicsContext gc = getGraphicsContext2D();
			Double nd;
			for (int i = 0; i < this.mapData.length; i++) { // run on h
				for (int j = 0; j < this.mapData[i].length; j++) { // run on w
					nd = ((this.mapData[i][j] - min) / (max - min));

					if (nd < 0.1 && nd >= 0) {
						gc.setFill(new Color(153.0 / 255, 255.0 / 255, 102.0 / 255, 1.0));

					}
					if (nd < 0.2 && nd >= 0.1) {
						gc.setFill(new Color(153.0 / 255, 230.0 / 255, 92.0 / 255, 1.0));
					} else if (nd < 0.3 && nd >= 0.2) {
						gc.setFill(new Color(153.0 / 255, 204.0 / 255, 82.0 / 255, 1.0));
					} else if (nd < 0.4 && nd >= 0.3) {
						gc.setFill(new Color(153.0 / 255, 178.0 / 255, 71.0 / 255, 1.0));
					} else if (nd < 0.5 && nd >= 0.4) {
						gc.setFill(new Color(153.0 / 255, 128.0 / 255, 51.0 / 255, 1.0));
					} else if (nd < 0.6 && nd >= 0.5) {
						gc.setFill(new Color(153.0 / 255, 102.0 / 255, 41.0 / 255, 1.0));
					} else if (nd < 0.7 && nd >= 0.6) {
						gc.setFill(new Color(153.0 / 255, 77.0 / 255, 31.0 / 255, 1.0));
					} else if (nd < 0.8 && nd >= 0.7) {
						gc.setFill(new Color(153.0 / 255, 51.0 / 255, 20.0 / 255, 1.0));
					} else if (nd < 0.9 && nd >= 0.8) {
						gc.setFill(new Color(153.0 / 255, 25.0 / 255, 2.0 / 255, 1.0));
					} else if (nd <= 1.0 && nd >= 0.9) {
						gc.setFill(new Color(153.0 / 255, 0.0 / 255, 0.0 / 255, 1.0));
					}
					gc.fillRect(j * w, i * h, w, h);

				}

			}

		}

	}

}
