package com.magc.sensecane.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfileControllerImpl extends AbstractController implements ProfileController {

	@FXML private Label firstName;
	@FXML private Label lastName;
	@FXML private Label dni;
	@FXML private Label token;
	@FXML private Label type;
	
	public ProfileControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void start() {
		User user = (User) Application.getInstance().get(Configuration.class).get("user");
		firstName.setText(user.getFirstName());
		lastName.setText(user.getLastName());
		dni.setText(user.getDni());
		token.setText(user.getToken());
		type.setText(user.getType());
	}
	
	@Override
	public void destroy() {
		List<Label> labels = Arrays.asList(new Label[] {firstName, lastName, dni, type});
		labels.forEach(e -> e.setText(""));
	}
}
