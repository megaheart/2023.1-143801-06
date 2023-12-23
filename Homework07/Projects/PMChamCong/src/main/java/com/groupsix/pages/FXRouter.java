package com.groupsix.pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.net.URL;
import java.util.HashMap;

public class FXRouter {
	// FXRouter Application Stage reference to set scenes
	private static Stage window;

	// FXRouter Application Stage reference to set scenes
	private static Application mainRef;

	private static class Routerinfo {
		public String routeLabel;
		public URL scenePath;
		public Class viewClass;
		public Class controllerClass;
	}

	private static HashMap<String, Routerinfo> routes = new HashMap<String, Routerinfo>();

	public static void bind(Application ref, Stage win, String winTitle, double winWidth, double winHeight) {
		window = win;
		mainRef = ref;
		window.setTitle(winTitle);
		window.setWidth(winWidth);
		window.setHeight(winHeight);
		window.resizableProperty().setValue(Boolean.FALSE);
	}

	public static void when(String routeLabel, URL scenePath,Class viewClass, Class controllerClass) {
		routes.put(routeLabel, new Routerinfo() {{
			this.routeLabel = routeLabel;
			this.scenePath = scenePath;
			this.viewClass = viewClass;
			this.controllerClass = controllerClass;
		}});
	}

	public static Object goTo(String routeLabel) {
		Routerinfo route = routes.get(routeLabel);
		if (route == null) {
			throw new RuntimeException("Route not found");
		}

		try {
			var controller = loadNewScene(route);
			return controller;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static Object loadNewScene(Routerinfo route) throws Exception{
		var loader = new FXMLLoader(route.scenePath);

//		if(route.viewClass != null) {
//			loader.setController(route.viewClass.getDeclaredConstructor().newInstance());
//		}

		Parent resource = loader.load();
		var view = loader.getController();
//		var controllerContructors = route.controllerClass.getDeclaredConstructors();
		var controller = route.controllerClass.getDeclaredConstructor(route.viewClass).newInstance(view);

		var scene = new Scene(resource);
		window.setScene(scene);
		window.show();

		return controller;
	}

}
