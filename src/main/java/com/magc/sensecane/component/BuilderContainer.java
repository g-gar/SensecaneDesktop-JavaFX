package com.magc.sensecane.component;

import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.Node;

public class BuilderContainer extends ConcurrentHashMap<Class<? extends ComponentController>, Builder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
