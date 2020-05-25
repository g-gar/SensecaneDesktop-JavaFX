package com.magc.sensecane.component;

import java.util.function.BiConsumer;

import javafx.scene.control.TableRow;

public class ModifiableTableRow<T> extends TableRow<T> {

	private final BiConsumer<TableRow<T>, T> fn;

	public ModifiableTableRow(BiConsumer<TableRow<T>, T> fn) {
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
		setWrapText(true);
	}

}
