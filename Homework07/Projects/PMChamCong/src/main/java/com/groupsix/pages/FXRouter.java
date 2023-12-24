package com.groupsix.pages;

import com.groupsix.pages.layouts.INavBar;
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
		public String _routeLabel;
		public URL _scenePath;
		public Class _viewClass;
		public Class _controllerClass;
		public String _navName;
	}

	private static HashMap<String, Routerinfo> routes = new HashMap<String, Routerinfo>();

	private static HashMap<String, Routerinfo> navRoutes = new HashMap<String, Routerinfo>();
	private static Routerinfo loginRoute;

	public static void bind(Application ref, Stage win, String winTitle, double winWidth, double winHeight) {
		window = win;
		mainRef = ref;
		window.setTitle(winTitle);
		window.setWidth(winWidth);
		window.setHeight(winHeight);
		window.resizableProperty().setValue(Boolean.FALSE);
	}

	public static void when(String routeLabel, URL scenePath, Class controllerClass, String navName) {
		routes.put(routeLabel, new Routerinfo() {{
			this._routeLabel = routeLabel;
			this._scenePath = scenePath;
//			this._viewClass = viewClass;
			this._controllerClass = controllerClass;
			this._navName = navName;
		}});
	}

	public static void addLoginPage(URL scenePath, Class controllerClass) {
		loginRoute = new Routerinfo() {{
			this._routeLabel = "login";
			this._scenePath = scenePath;
//			this._viewClass = viewClass;
			this._controllerClass = controllerClass;
		}};
	}

	public static void addNav(String navName, URL scenePath) {
		navRoutes.put(navName, new Routerinfo() {{
			this._routeLabel = navName;
			this._scenePath = scenePath;
		}});
	}

	public static Object goTo(String routeLabel) {
		Routerinfo route = routes.get(routeLabel);
		if (route == null) {
			throw new RuntimeException("Route not found");
		}

		try {
			var controller = loadNewSubScene(route);
			return controller;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static INavBar loadNewScene(Routerinfo route) throws Exception{
		var loader = new FXMLLoader(route._scenePath);

		Parent resource = loader.load();
		var view = loader.getController();

		var scene = new Scene(resource);
		window.setScene(scene);
		window.show();

		if (view instanceof INavBar) {
			return (INavBar) view;
		}

		return null;
	}
	private static Object loadNewSubScene(Routerinfo route) throws Exception{
		var loader = new FXMLLoader(route._scenePath);

//		if(route.viewClass != null) {
//			loader.setController(route.viewClass.getDeclaredConstructor().newInstance());
//		}

		Parent resource = loader.load();
		var view = loader.getController();
//		var controllerContructors = route.controllerClass.getDeclaredConstructors();
		var controller = route._controllerClass.getDeclaredConstructor(view.getClass()).newInstance(view);

		var scene = new Scene(resource);
		window.setScene(scene);
		window.show();

		return controller;
	}

}
