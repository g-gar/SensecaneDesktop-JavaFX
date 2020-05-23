package com.magc.sensecane.controller;

import com.magc.sensecane.framework.javafx.controller.Controller;
import com.magc.sensecane.model.domain.Message;

public interface TicketController extends Controller {
   
	void sendMessage();
	void showMessages(Message... messages);
}
