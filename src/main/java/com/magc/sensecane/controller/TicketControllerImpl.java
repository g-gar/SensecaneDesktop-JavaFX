package com.magc.sensecane.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.component.ModifiableListCell;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.Message;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.service.MessageService;
import com.magc.sensecane.service.UserService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class TicketControllerImpl extends AbstractController implements TicketController {

	@FXML
	private ListView<User> users;
	@FXML
	private ListView<Message> messages;
	@FXML
	private TextArea messageBody;

	public TicketControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void start() {
		UserService.getRelatedUsers((User) Application.getInstance().get(Configuration.class).get("user"), users -> {
			Application.getInstance().execute(() -> {
				Configuration conf = Application.getInstance().get(Configuration.class);
				TicketControllerImpl.this.users.setItems(FXCollections.observableList(
						users.stream().filter(e -> !e.getId().equals(conf.get("user"))).collect(Collectors.toList())));
			});
		});
		
		users.setCellFactory(callback -> new ModifiableListCell<User>((ListCell<User> cell, User user) -> {
			Application.getInstance().execute(() -> {
				if (user != null) {
					cell.setText(user.getUsername());
				}
			});
		}));

		messages.setCellFactory(
				item -> new ModifiableListCell<Message>((ListCell<Message> cell, Message message) -> {
					Application.getInstance().execute(() -> {
						if (message != null) {
							Configuration conf = Application.getInstance().get(Configuration.class);
							User user = users.getItems().stream().filter(e -> e.getId().equals(message.getFrom()))
									.findFirst().orElse((User) conf.get("user"));
							cell.setText(String.format("%s (%s): %s", user.getUsername(), message.getTimestamp(),
									message.getMessage()));
						}
					});
				}));

		users.getSelectionModel().selectedItemProperty().addListener((obs, old, user) -> {
			User from = (User) Application.getInstance().get(Configuration.class).get("user");
			MessageService.getMessages(from, user, messages -> {
				TicketControllerImpl.this.messages.getItems().clear();
				Application.getInstance().execute(() -> {
					TicketControllerImpl.this.showMessages(messages.stream()
							.filter( message -> message.getTo().equals(user.getId()) || message.getTo().equals(from.getId()))
							.collect(Collectors.toList())
							.toArray(new Message[] {}));
				});
			});
		});
	}

	@Override
	public void destroy() {
		users.getItems().clear();
		messages.getItems().clear();
	}

	public void loadMessage(Message message) {
		User to = users.getItems().stream().filter(e -> e.getId().equals(message.getTo())).findFirst().orElse(null);
		messages.getItems().add(message);
	}

	@Override
	public void sendMessage() {
		System.out.println(messageBody.getText());
	}

	@Override
	public void showMessages(Message... messages) {
		Application.getInstance().execute(() -> {
			TicketControllerImpl.this.messages.getItems().addAll(messages);
		}); 
	}

}
