package com.magc.sensecane.controller;

import java.net.URL;

import com.magc.sensecane.Application;
import com.magc.sensecane.framework.javafx.controller.AbstractController;
import com.magc.sensecane.framework.javafx.controller.ControllerContainer;

public class SidebarControllerImpl extends AbstractController implements SidebarController {

	public SidebarControllerImpl(URL fxml) {
		super(fxml);
	}

	@Override
	public void loadProfileSubsystem() {
		Application app = Application.getInstance();
		ControllerContainer container = app.get(ControllerContainer.class);
		MainController controller = (MainController) container.get(MainController.class);
		controller.loadProfileSubsystem();
	}

	@Override
	public void loadNetworkSubsystem() {
		Application app = Application.getInstance();
		ControllerContainer container = app.get(ControllerContainer.class);
		MainController controller = (MainController) container.get(MainController.class);
		controller.loadNetworkSubsystem();
	}

	@Override
	public void loadSensorSubsystem() {
		Application app = Application.getInstance();
		ControllerContainer container = app.get(ControllerContainer.class);
		MainController controller = (MainController) container.get(MainController.class);
		controller.loadSensorSubsystem();
	}

	@Override
	public void loadTicketSubsystem() {
		Application app = Application.getInstance();
		ControllerContainer container = app.get(ControllerContainer.class);
		MainController controller = (MainController) container.get(MainController.class);
		controller.loadTicketSubsystem();
	}

	@Override
	public void logout() {
		Application app = Application.getInstance();
		ControllerContainer container = app.get(ControllerContainer.class);
		MainController controller = (MainController) container.get(MainController.class);
		controller.logout();
	}
	
	

}
