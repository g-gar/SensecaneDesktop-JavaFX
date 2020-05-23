package com.magc.sensecane.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.service.AuthService;
import com.magc.sensecane.service.ErrorService;
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
//		username.setText("patient1");
//		password.setText("patient1");
	}
	
	@Override
	public void destroy() {
		password.setText("");
	}

	@Override @FXML
	public void login() {
		try {
			AuthService.authenticate(username.getText(), password.getText(), user -> {
				System.out.println(user);
				Application.getInstance().execute(() -> {
					if (user != null) {
						Application.getInstance().get(Configuration.class).put("user", user);
						ChangeView.execute(MainController.class);
					} else {
						System.out.println("error");
						ErrorService.notifyError("Invalid username or password");
						password.setText("");
					}
				});
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void exit() {
		System.exit(0);
	}

}
