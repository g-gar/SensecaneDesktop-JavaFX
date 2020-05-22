package com.magc.sensecane.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.component.Builder;
import com.magc.sensecane.component.BuilderContainer;
import com.magc.sensecane.component.ModifiableListCell;
import com.magc.sensecane.controller.component.MessageComponent;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.Message;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.service.MessageService;
import com.magc.sensecane.service.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class TicketControllerImpl extends AbstractController implements TicketController {

	@FXML private ListView<User> users;
	@FXML private ListView<Message> messages;
	@FXML private TextArea messageBody;
	
	public TicketControllerImpl(URL fxml) {
		super(fxml);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users.setCellFactory(callback -> new ModifiableListCell<User>((ListCell<User> cell, User user) -> {
			Application.getInstance().execute(() -> {
				if (user != null) {
					cell.setText(user.getUsername());
				}
			});
		}));
		
		messages.setCellFactory(callback -> new ModifiableListCell<Message>((ListCell<Message> cell, Message message) -> {
			Application.getInstance().execute(() -> {
				if (message != null) {
					Configuration conf = Application.getInstance().get(Configuration.class);
					User user = users.getItems().stream()
							.filter(e -> e.getId().equals(message.getFrom()))
							.findFirst()
							.orElse((User) conf.get("user"));
					cell.setText(String.format("%s (%s): %s", user.getUsername(), message.getTimestamp(), message.getMessage()));
				}
			});
		}));
		
		users.getSelectionModel().selectedItemProperty().addListener((obs, old, user) -> {
			User from = (User) Application.getInstance().get(Configuration.class).get("user");
			MessageService.getMessages(from, user, messages -> {
//				TicketControllerImpl.this.messages.getChildren().removeAll(TicketControllerImpl.this.messages.getChildren());
				messages.stream()
					.filter(message -> message.getTo().equals(user.getId()))
					.forEach(e -> TicketControllerImpl.this.loadMessageComponent(e));
			});
		});
	}

	@Override
	public void start() {
		User user = (User) Application.getInstance().get(Configuration.class).get("user");
		UserService.getRelatedUsers(user, users -> {
			Application.getInstance().execute(() -> {
				TicketControllerImpl.this.users.setItems(FXCollections.observableList(users));
			});
		});
	}
	
	@Override
	public void destroy() {
		users.getItems().clear();
	}
	
	public void loadMessageComponent(Message message) {
		User to = users.getItems().stream().filter(e -> e.getId().equals(message.getTo())).findFirst().orElse(null);
//		this.loadMessageComponent(message.getTimestamp(), to.getUsername(), message.getMessage());
		messages.getItems().add(message);
	}
	
	public void loadMessageComponent(Long date, String username, String body) {
//		System.out.println(date + " " + username + " " + body);
//		Application app = Application.getInstance();
//		Builder builder = app.get(BuilderContainer.class).get(MessageComponent.class);
//		System.out.println(builder.getFxml());
//		MessageComponent m = new MessageComponent(builder.getFxml());
//		m.setMinHeight(50.0);
//		m.setMinWidth(50.0);
//		m.setPrefWidth(100);
//		m.setPrefHeight(50);
//		m.setVisible(true);
//		System.out.println(m);
//		m.start(date, username, body);
//		m.prefWidthProperty().bind(TicketControllerImpl.this.messages.prefWidthProperty());
//		messages.getItems().add(m)
//		System.out.println(m);
//		this.setVisible(true);
//		Application.getInstance().stage.show();
	}

	@Override
	public void sendMethod() {
		
	}
}
