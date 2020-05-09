package com.magc.sensecane.component;

import java.util.function.BiConsumer;

import javafx.scene.control.ListCell;

public class ModifiableListCell<T> extends ListCell<T> {
	
	private final BiConsumer<ListCell<T>, T> fn;
	
	public ModifiableListCell(BiConsumer<ListCell<T>, T> fn) {	
		this.fn = fn;
	}
	
	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
	    if (empty || item == null) {
	        setText(null);
	        setGraphic(null);
	    } else if (this.fn != null) {
			this.fn.accept(this, item);
		}
	}	
}
