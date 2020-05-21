package com.magc.sensecane;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.magc.sensecane.component.Builder;
import com.magc.sensecane.component.BuilderContainer;
import com.magc.sensecane.component.ComponentController;
import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.container.DefaultContainer;
import com.magc.sensecane.framework.javafx.controller.Controller;
import com.magc.sensecane.framework.javafx.controller.ControllerContainer;
import com.magc.sensecane.framework.utils.LoadResource;

public class ConfigurationJsonParser implements JsonDeserializer<Container>{
	
	private final Container container;
	
	public ConfigurationJsonParser() {
		this.container = new DefaultContainer() {};
	}
	
	public ConfigurationJsonParser(Container container) {
		this.container = container;
	}

	@Override
	public Container deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		
		if (obj.has("ui")) {
			JsonObject ui = obj.getAsJsonObject("ui");
			ControllerContainer controllerContainer = container.register(ControllerContainer.class, new ControllerContainer());
			BuilderContainer buildercontainer = container.register(BuilderContainer.class, new BuilderContainer());
			
			if (ui.has("controller")) {
				JsonObject ui_controller = ui.get("controller").getAsJsonObject();
				for (String key : ui_controller.keySet()) {
					try {
						Class<Controller> controller = (Class<Controller>) Class.forName(key);
						JsonObject temp = ui_controller.getAsJsonObject(key);
						if (temp.has("implementation") && temp.has("fxml")) {
							URL url = container.get(LoadResource.class).execute(temp.get("fxml").getAsString()).toURL();
							Class impl = Class.forName(temp.get("implementation").getAsString());
							Constructor constructor = impl.getDeclaredConstructor(URL.class);
							controllerContainer.put(controller, (Controller) constructor.newInstance(url));
						}
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
			
			if (ui.has("component")) {
				JsonObject ui_component = ui.get("component").getAsJsonObject();
				for (String key : ui_component.keySet()) {
					try {
						String fxml = ui_component.get(key).getAsString();
						URL url = container.get(LoadResource.class).execute(fxml).toURI().toURL();
						Class<ComponentController> c = (Class<ComponentController>) Class.forName(key);
						buildercontainer.put(c, new Builder(c, url) {});
					} catch (IllegalArgumentException | SecurityException | ClassNotFoundException | MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (obj.has("server")) {
			Configuration config = this.container.get(Configuration.class);
			config.put("serverUrl", obj.get("server").getAsString());
		}
		
		return container;
	}

}
