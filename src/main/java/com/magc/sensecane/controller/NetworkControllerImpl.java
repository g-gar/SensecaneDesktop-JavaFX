package com.magc.sensecane.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXTextField;
import com.magc.sensecane.Application;
import com.magc.sensecane.component.ModifiableListCell;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.service.UserService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;

public class NetworkControllerImpl extends AbstractController implements NetworkController {

	@FXML
	private ListView<User> listview;
	@FXML
	private JFXTextField userSelector;

	public NetworkControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void start() {
		listview.setCellFactory(callback -> new ModifiableListCell<User>((ListCell<User> cell, User user) -> {
			Application.getInstance().execute(() -> {
				if (user != null) {
					cell.setText(user.getUsername());
				}
			});
		}));

		UserService.getUserList((users) -> {
			listview.getItems().clear();
			listview.getItems().addAll(users);
		});
	}

	@FXML
	@Override
	public void filterUsers(KeyEvent event) {
		final String text = userSelector.getText().trim().toLowerCase();
		final List<Predicate<User>> predicates = new ArrayList<Predicate<User>>();

		if (true) {
			UserService.getUserList(users -> {
				Application.getInstance().execute(() -> {
					if (text.length() > 0) {
						for (String part : text.split(" "))
							predicates.add(user -> user.getUsername().contains(part));

						listview.setItems(FXCollections.observableArrayList(users.stream()
							.filter(user -> predicates.stream().map(e -> e.test(user)).reduce((a, b) -> a && b).get())
							.collect(Collectors.toList()))
						);
					} else {
						listview.setItems(FXCollections.observableArrayList(users));
					}
				});
			});
		}
	}

	@FXML
	@Override
	public void registerUser() {

	}

}
