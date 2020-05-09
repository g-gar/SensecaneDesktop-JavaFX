package com.magc.sensecane.controller;

import java.net.URL;

import com.jfoenix.controls.JFXComboBox;
import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.component.ModifiableListCell;
import com.magc.sensecane.component.ModifiableTextFieldListCell;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.Sensor;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.service.UserService;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;

public class SensorControllerImpl extends AbstractController implements SensorController {

	@FXML private JFXComboBox<User> patients;
	@FXML private JFXComboBox<Sensor> sensors;
	
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
		
		patients.setButtonCell(patients.getCellFactory().call(null));
		
		UserService.getUserList(users -> {
			Application.getInstance().execute(() -> {
				patients.setItems(FXCollections.observableArrayList(users));
			}); 
		});
	}

	@FXML
	@Override
	public void selectSensors(ActionEvent event) {
		System.out.println(event.getSource().getClass());
		UserService.getSensors((User) Application.getInstance().get(Configuration.class).get("user"), sensors -> {
			Application.getInstance().execute(() -> {
//				patients
			});
		});
	}

}
