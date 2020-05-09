package com.magc.sensecane.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.framework.javafx.controller.Controller;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.model.domain.UserType;
import com.magc.sensecane.service.AuthService;
import com.magc.sensecane.util.ChangeView;
import com.magc.sensecane.util.HttpUtil;
import com.magc.sensecane.util.JsonUtil;

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
	public void initialize(URL location, ResourceBundle resources) {
		username.setText("patient");
		password.setText("patient");
	}

	@Override
	public void login() {
		try {
			AuthService.authenticate(username.getText(), password.getText(), user -> {
				Application.getInstance().execute(() -> {
					if (user != null) {
						Application.getInstance().get(Configuration.class).put("user", user);
						ChangeView.execute(MainController.class);
					} else {
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
