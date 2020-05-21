package com.magc.sensecane.controller.component;

import java.net.URL;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.magc.sensecane.component.ComponentController;

import javafx.fxml.FXML;

public class MessageComponent extends ComponentController {
	
	@FXML private JFXTextField date;
	@FXML private JFXTextField from;
	@FXML private JFXTextArea body;
	
	public MessageComponent(URL fxml) {
		super(fxml);
	}

	@Override
	public void start(Object... params) {
		date.setText(params[0].toString());
		from.setText(params[1].toString());
		body.setText(params[2].toString());
	}

	@Override
	public void destroy() {
		date.setText("");
		from.setText("");
		body.setText("");
	}

}
