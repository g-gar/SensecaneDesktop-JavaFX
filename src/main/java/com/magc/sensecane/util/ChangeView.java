package com.magc.sensecane.util;

import com.magc.sensecane.Application;
import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.javafx.controller.Controller;
import com.magc.sensecane.framework.javafx.controller.ControllerContainer;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChangeView {

	public static <T> void execute(Class<T> c) {
		Container container = Application.getInstance();
		Controller controller = null;
		Stage stage = null;

		try {
			controller = container.get(ControllerContainer.class).get(c);
			stage = Application.getInstance().stage;
			
			double w = stage.getWidth();
			double h = stage.getHeight();
			
			stage.setScene(controller.get());
			stage.show();
			stage.setWidth(w);
			stage.setHeight(h);
			
			controller.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
