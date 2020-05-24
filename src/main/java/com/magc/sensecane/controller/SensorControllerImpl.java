package com.magc.sensecane.controller;

import java.net.URL;

import com.jfoenix.controls.JFXComboBox;
import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.component.ModifiableListCell;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.Sensor;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.service.LoggerService;
import com.magc.sensecane.service.SensorService;
import com.magc.sensecane.service.UserService;
import com.magc.sensecane.util.JavaFXChartUtils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class SensorControllerImpl extends AbstractController implements SensorController {

	@FXML
	private JFXComboBox<User> patients;
	@FXML
	private JFXComboBox<Sensor> sensors;
	@FXML
	private LineChart<Long, Double> linechart;
	@FXML
	private NumberAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	
	@FXML
	private Label patientsLabel;

	public SensorControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void start() {
		
		patients.setCellFactory(callback -> new ModifiableListCell<User>((ListCell<User> cell, User user) -> {
			Application.getInstance().execute(() -> {
				cell.setText(user.getUsername());
			});
		}));
		
		sensors.setCellFactory(callback -> new ModifiableListCell<Sensor>((ListCell<Sensor> cell, Sensor sensor) -> {
			Application.getInstance().execute(() -> {
				cell.setText(sensor.getName());
			});
		}));
		
		patients.getSelectionModel().selectedItemProperty().addListener((obs, old, user) -> {
			sensors.getItems().clear();
			SensorService.getSensors(user, sensors -> {
				Application.getInstance().execute(() -> {
					SensorControllerImpl.this.sensors.setItems(FXCollections.observableList(sensors));
				});
			});
		});
		
		sensors.getSelectionModel().selectedItemProperty().addListener((obs, old, sensor) -> {
			if (sensors.getItems().size() > 0) {
				if (sensor != null) {
					SensorService.getSensorData(sensor, values -> {
						XYChart.Series<Long, Double> serie = JavaFXChartUtils.generate(sensor.getName(), values, e -> new Data<Long, Double>(e.getTimestamp(), e.getValue()));
						Application.getInstance().execute(() -> {
							JavaFXChartUtils.cleanChartSeries(linechart);				
							JavaFXChartUtils.plotSerie(linechart, serie);
						});
					});
				} else {
					LoggerService.notifyError("No sensor provided");
				}
			}
		});
		
		patients.setButtonCell(patients.getCellFactory().call(null));
		sensors.setButtonCell(sensors.getCellFactory().call(null));
		
		this.fillComboBoxes((User) Application.getInstance().get(Configuration.class).get("user"));
	}

	@Override
	public void destroy() {
		
		linechart.getData().forEach(e -> e.getData().clear());
		linechart.getData().clear();
		
		patients.getItems().clear();
		sensors.getItems().clear();
		
		patients.getSelectionModel().clearSelection();
		sensors.getSelectionModel().clearSelection();
		
		patientsLabel.setVisible(true);
		patients.setVisible(true);
	}
	
	private void fillComboBoxes(User user) {
		switch(user.getType()) {
			case "doctor":
				UserService.getUsers(users -> {
					Application.getInstance().execute(() -> {
						patients.setItems(FXCollections.observableArrayList(users));
					});
				});
				break;
			case "patient":
				patientsLabel.setVisible(false);
				patients.setVisible(false);
				SensorService.getSensors(user, sensors -> {
					Application.getInstance().execute(() -> {
						SensorControllerImpl.this.sensors.getItems().addAll(sensors);
					});
				});
				break;
			case "carer":
				break;
			default:
				LoggerService.notifyError(String.format("User type [%s] is not supported", user.getType()));
		}
	}
}
