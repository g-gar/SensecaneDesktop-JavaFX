package com.magc.sensecane.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXTextField;
import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.component.ModifiableTableRow;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.service.LoggerService;
import com.magc.sensecane.service.UserService;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class NetworkControllerImpl extends AbstractController implements NetworkController {

	@FXML private TableView<User> table;
	@FXML private JFXTextField search;

	public NetworkControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void start() {
//		table.setCellFactory(cell -> new ModifiableTableCell<User>((TableCell<User> cell, User user) -> {
//			Application.getInstance().execute(() -> {
//				if (user != null) {
//					cell.setText(user.getUsername());
//				}
//			});
//		}));
		
		table.setRowFactory(table -> new ModifiableTableRow<User>((TableRow<User> row, User user) -> {
			Application.getInstance().execute(() -> {
				System.out.println(user);
			});
		}));
		
		table.getColumns()
			.stream()
			.map(col -> (TableColumn<User, String>) col)
			.forEach(column -> {
				Callback<CellDataFeatures<User, String>, ObservableValue<String>> callback = null;
				switch (column.getId()) {
					case "username":
						callback = new PropertyValueFactory("username");
						break;
					case "firstName":
						callback = new PropertyValueFactory("firstName");
						break;
					case "lastName":
						callback = new PropertyValueFactory("lastName");
						break;
					case "dni":
						callback = new PropertyValueFactory("dni");
						break;
					case "type":
						callback = new PropertyValueFactory("type");
						break;
				}
				column.setCellValueFactory(callback);
				column.prefWidthProperty().bind(table.widthProperty().divide(table.getColumns().size()));
				column.setCellFactory(tablecolumn -> {
					TableCell<User, String> cell = TextFieldTableCell.<User>forTableColumn().call(tablecolumn);
					cell.itemProperty().addListener((obs, old, str) -> {
						if (cell.getTableRow() == null) cell.setEditable(false);
						else if (cell.getTableRow().getItem() == null) cell.setEditable(false);
						else cell.setEditable(true);
					});
					return cell;
				});
			});
		
		UserService.getRelatedUsers((User) Application.getInstance().get(Configuration.class).get("user"), users -> {
			Application.getInstance().execute(() -> {
				if (!users.isEmpty()) {
					table.getItems().clear();
					table.getItems().addAll(FXCollections.observableArrayList(users));
				} else {
					LoggerService.notifyError("No users available");
				}
			});
		});
	}
	
	@Override
	public void destroy() {
		table.getItems().clear();
	}

	@FXML
	@Override
	public void filterUsers() {
		final String text = search.getText().trim().toLowerCase();
		final List<Predicate<User>> predicates = new ArrayList<Predicate<User>>();

		if (true) {
			UserService.getRelatedUsers((User) Application.getInstance().get(Configuration.class).get("user"), users -> {
				Application.getInstance().execute(() -> {
					if (text.length() > 0) {
						for (String part : text.split(" "))
							predicates.add(user -> user.getUsername().contains(part));

						table.setItems(FXCollections.observableArrayList(users.stream()
							.filter(user -> predicates.stream().map(e -> e.test(user)).reduce((a, b) -> a && b).get())
							.collect(Collectors.toList()))
						);
					} else {
						table.setItems(FXCollections.observableArrayList(users));
					}
				});
			});
		}
	}

	@Override
	public void registerUser() {
		
	}

}
