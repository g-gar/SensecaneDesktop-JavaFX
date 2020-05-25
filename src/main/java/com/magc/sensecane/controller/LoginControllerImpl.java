package com.magc.sensecane.controller;

import java.net.URL;
import java.util.function.Consumer;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.service.AuthService;
import com.magc.sensecane.util.ChangeView;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginControllerImpl extends AbstractController implements LoginController {

	@FXML
	private TextField username;
	@FXML
	private TextField password;

	public LoginControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void start() {
		username.setText("paciente1");
		password.setText("paciente1");
	}
	
	@Override
	public void destroy() {
		password.setText("");
	}

	@Override @FXML
	public void login() {
		Consumer<User> onComplete = user -> {
			Application.getInstance().execute(() -> {
				if (user != null) {
					Application.getInstance().get(Configuration.class).put("user", user);
					ChangeView.execute(MainController.class);
				}
			});
		};
		Runnable onError = () -> {
			Application.getInstance().execute(() -> {
				password.setText("");
			});
		};
		AuthService.authenticate(username.getText(), password.getText(), onComplete, onError);
	}

	@Override
	public void exit() {
		System.exit(0);
	}

}
