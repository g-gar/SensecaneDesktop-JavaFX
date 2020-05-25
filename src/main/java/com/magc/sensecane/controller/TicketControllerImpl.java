package com.magc.sensecane.controller;

import java.net.URL;
import java.util.stream.Collectors;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.component.ModifiableListCell;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.Message;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.service.LoggerService;
import com.magc.sensecane.service.MessageService;
import com.magc.sensecane.service.UserService;
import com.magc.sensecane.util.NumberUtils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
			if (user != null) {
				MessageService.getMessages(from, user, messages -> {
					TicketControllerImpl.this.messages.getItems().clear();
					Application.getInstance().execute(() -> {
						TicketControllerImpl.this.showMessages(messages.stream()
							.filter( message -> message.getTo().equals(user.getId()) || message.getTo().equals(from.getId()))
							.sorted((a,b) -> NumberUtils.compare(a.getTimestamp(), b.getTimestamp()))
							.collect(Collectors.toList())
							.toArray(new Message[] {}));
					});
				});
			}
		});
	}

	@Override
	public void destroy() {
		messages.getItems().clear();
		users.getItems().clear();
		
		messages.getSelectionModel().clearSelection();
		users.getSelectionModel().clearSelection();
	}

	public void loadMessage(Message message) {
		User to = users.getItems().stream().filter(e -> e.getId().equals(message.getTo())).findFirst().orElse(null);
		messages.getItems().add(message);
	}

	@Override
	public void sendMessage() {
		Application app = Application.getInstance();
		User from = (User) app.get(Configuration.class).get("user");
		User to = users.getSelectionModel().getSelectedItem();
		MessageService.sendMessage(from, to, messageBody.getText(), message -> {
			app.execute(() -> {
				messages.getItems().add(message);
				messageBody.setText("");
			});
		}, () -> {
			messageBody.setText("");
			LoggerService.notifyError("Error sending message");
		});
	}

	@Override
	public void showMessages(Message... messages) {
		Application.getInstance().execute(() -> {
			TicketControllerImpl.this.messages.getItems().addAll(messages);
		}); 
	}

}
