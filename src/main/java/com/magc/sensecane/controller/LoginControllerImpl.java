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
		username.setText("patient1");
		password.setText("patient1");
	}
	
	@Override
	public void destroy() {
		password.setText("");
	}

	@Override @FXML
	public void login() {
		Consumer<User> onComplete = user -> {
			System.out.println(user);
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
		
//		Application.getInstance().get(Configuration.class).put("user", new User(1, "admin", "2342352341532", "admin", "admin", "doctor", "324123513451324n"));
//		String[] types = new String[] {"patient", "carer", "doctor"};
//		for (int i = 0; i < 200000000; i++) {
//			int rand = new Random().nextInt(types.length - 1 + 1);
//			String type = types[rand];
//			String dni = NifUtil.generaNif();
//			
//			while (dni == null) { dni = NifUtil.generaNif();}
//			
//			User usr = new User(null, type+i, dni, type, ""+i, type);
//			UserService.createUser(usr, type+i, u -> {}, () -> {});
//		}
	}

	@Override
	public void exit() {
		System.exit(0);
	}

}
