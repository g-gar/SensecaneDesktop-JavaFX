package com.magc.sensecane.controller;

import java.net.URL;

import com.magc.sensecane.Application;
import com.magc.sensecane.component.BuilderContainer;
import com.magc.sensecane.controller.component.MessageComponent;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.Message;
import com.magc.sensecane.model.domain.User;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TicketControllerImpl extends AbstractController implements TicketController {

	@FXML private ListView<User> users;
	@FXML private ListView<Message> messages;
	@FXML private TextArea messageBody;
	
	public TicketControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void start() {
		
		
		
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
