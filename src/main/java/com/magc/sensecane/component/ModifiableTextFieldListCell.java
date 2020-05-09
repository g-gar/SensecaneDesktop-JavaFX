package com.magc.sensecane.component;

import java.util.function.BiConsumer;

import javafx.scene.control.ListCell;

public class ModifiableTextFieldListCell<T> extends ModifiableListCell<T> {

	public ModifiableTextFieldListCell(BiConsumer<ListCell<T>, T> fn) {
		super(fn);
	}
	
}
