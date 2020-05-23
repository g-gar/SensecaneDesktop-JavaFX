package com.magc.sensecane.util;

import com.magc.sensecane.Application;
import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.javafx.controller.Controller;
import com.magc.sensecane.framework.javafx.controller.ControllerContainer;

import javafx.stage.Stage;

public class ChangeView {

	public static <T> void execute(Class<T> c) {
		Container container = Application.getInstance();
		Controller controller = null;
		Stage stage = null;

		try {
			controller = container.get(ControllerContainer.class).current;
			if (controller != null) {
				System.out.println(String.format("[%s] destroy\n", controller.getClass()));
				controller.destroy();
			}
			
			controller = container.get(ControllerContainer.class).get(c);
			container.get(ControllerContainer.class).current = controller;
			stage = Application.stage;
			
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
