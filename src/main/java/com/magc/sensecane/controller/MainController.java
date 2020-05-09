package com.magc.sensecane.controller;

import com.magc.sensecane.framework.javafx.controller.Controller;

public interface MainController extends Controller {

	void loadProfileSubsystem();
	void loadNetworkSubsystem();
	void loadSensorSubsystem();
	void loadTicketSubsystem();
	
	void logout();
	
}
