package com.magc.sensecane.component;

import java.util.function.BiConsumer;

import com.magc.sensecane.util.PlatformRunner;

import javafx.scene.control.TableCell;

public class ModifiableTableCell<S,T> extends TableCell<S,T>{

private final BiConsumer<TableCell<S,T>, T> fn;
	
	public ModifiableTableCell(BiConsumer<TableCell<S,T>, T> fn) {	
		this.fn = fn;
	}
	
	@Override
	protected void updateItem(T item, boolean empty) {
		PlatformRunner.execute(() -> {
			super.updateItem(item, empty);
		    if (empty || item == null) {
		        setText(null);
		        setGraphic(null);
		    } else if (this.fn != null) {
				this.fn.accept(this, item);
			}
		    setWrapText(true);
		});
	}
	
}
