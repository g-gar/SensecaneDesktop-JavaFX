package com.magc.sensecane.controller;

import java.net.URL;

import com.jfoenix.controls.JFXComboBox;
import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.component.ModifiableListCell;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.Sensor;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.service.ErrorService;
import com.magc.sensecane.service.SensorService;
import com.magc.sensecane.service.UserService;
import com.magc.sensecane.util.JavaFXChartUtils;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
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
			if (sensor != null) {
				SensorService.getSensorData(sensor, values -> {
					XYChart.Series<Long, Double> serie = JavaFXChartUtils.generate(sensor.getName(), values, e -> new Data<Long, Double>(e.getTimestamp(), e.getValue()));
					Application.getInstance().execute(() -> {
						JavaFXChartUtils.cleanChartSeries(linechart);				
						JavaFXChartUtils.plotSerie(linechart, serie);
					});
				});
			} else {
				ErrorService.notifyError("No sensor provided");
			}
		});
		
		patients.setButtonCell(patients.getCellFactory().call(null));
		sensors.setButtonCell(sensors.getCellFactory().call(null));
		
		UserService.getUsers(users -> {
			Application.getInstance().execute(() -> {
				patients.setItems(FXCollections.observableArrayList(users));
			});
		});
	}

	@FXML
	@Override
	public void selectSensors(ActionEvent event) {
		System.out.println(event.getSource().getClass());
		SensorService.getSensors((User) Application.getInstance().get(Configuration.class).get("user"), sensors -> {
			Application.getInstance().execute(() -> {
				System.out.println(sensors);
			});
		});
	}
}
