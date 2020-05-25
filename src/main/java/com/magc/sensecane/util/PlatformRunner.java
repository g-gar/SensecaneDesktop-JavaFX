package com.magc.sensecane.util;

import javafx.application.Platform;

public class PlatformRunner {

	public static void execute(Runnable runnable) {
		if (Platform.isFxApplicationThread()) {
			runnable.run();
		} else {
			Platform.runLater(runnable);
		}
	}
	
}
