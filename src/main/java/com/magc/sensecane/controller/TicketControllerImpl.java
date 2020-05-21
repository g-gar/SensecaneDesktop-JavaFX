package com.magc.sensecane.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.magc.sensecane.Application;
import com.magc.sensecane.component.BuilderContainer;
import com.magc.sensecane.controller.component.MessageComponent;
import com.magc.sensecane.framework.javafx.controller.AbstractController;

import javafx.stage.Stage;

public class TicketControllerImpl extends AbstractController implements TicketController {

	public TicketControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void loadMessageComponent(String date, String username, String body) {
		Application app = Application.getInstance();
		Stage stage = app.stage;
		
		MessageComponent m = app.get(BuilderContainer.class).get(MessageComponent.class).build();
		stage.setScene(m.get());
		m.start(date, username, body);
		stage.show();
	}

	@Override
	public void sendMethod() {
	}
}
