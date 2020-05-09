package com.magc.sensecane.controller;

import java.net.URL;

import com.magc.sensecane.Application;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.framework.javafx.controller.Controller;
import com.magc.sensecane.framework.javafx.controller.ControllerContainer;
import com.magc.sensecane.util.ChangeView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainControllerImpl extends AbstractController implements MainController {
	
	@FXML private HBox left;
	@FXML private HBox top;
	@FXML private HBox container;

	public MainControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void start() {
		Application app = Application.getInstance();
		ControllerContainer container = app.get(ControllerContainer.class);
		Controller controller = container.get(SidebarController.class);
		app.execute(new Runnable() {
			@Override public void run() {
				ObservableList<Node> children = MainControllerImpl.this.left.getChildren();
//				children.removeAll(children);
//				children.add((Node) controller);
				
				left.setStyle("-fx-background-color: #660000");
				
//				((HBox) MainControllerImpl.this).widthProperty().addListener((obs, old, val) -> {
//					left.setPrefWidth(val.doubleValue() * 0.2);
//					((HBox) controller).setPrefWidth(left.getPrefWidth());
//				});
//				((HBox) MainControllerImpl.this).heightProperty().addListener((obs, old, val) -> {
//					left.setPrefHeight(val.doubleValue());
//				});
				
//				left = (HBox) controller;
				BorderPane pane = (BorderPane) MainControllerImpl.this.lookup("BorderPane");
				pane.setLeft((Node) controller);
			}
		});
		
		this.loadProfileSubsystem();
	}
	
	@Override
	public void destroy() {
		getChildren().remove(left);
		ObservableList<Node> children = left.getChildren();
		left.getChildren().remove(0, children.size());
	}

	@Override
	public void loadProfileSubsystem() {
		load(ProfileController.class);
	}

	@Override
	public void loadNetworkSubsystem() {
		load(NetworkController.class);
	}

	@Override
	public void loadSensorSubsystem() {
		load(SensorController.class);
	}

	@Override
	public void loadTicketSubsystem() {
		load(TicketController.class);
	}

	private void load(Class c) {
		Application app = Application.getInstance();
		Stage stage = app.stage;
		ControllerContainer container = app.get(ControllerContainer.class);
		
		Controller controller = container.get(c);
		app.execute(() -> {
			BorderPane pane = (BorderPane) MainControllerImpl.this.lookup("BorderPane");
			pane.setCenter((Node) controller);
			Application.getInstance().get(ControllerContainer.class).get(c).start();
		});
	}

	@Override
	public void logout() {
		try {
			ChangeView.execute(LoginController.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
