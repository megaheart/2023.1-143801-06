package com.groupsix.pages;

import com.groupsix.pages.layouts.INavBar;
import com.groupsix.user.User;
import com.groupsix.user.UserService;
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
	private static INavBar currentNav;
	private static String currentRouteLabel = "";

	public static void bind(Application ref, Stage win, String winTitle, double winWidth, double winHeight) {
		window = win;
		mainRef = ref;
		window.setTitle(winTitle);
		window.setWidth(winWidth);
		window.setHeight(winHeight);
		window.resizableProperty().setValue(Boolean.FALSE);
	}

	public static void when(String routeLabel, URL scenePath, Class controllerClass, String role) {
		routes.put(routeLabel, new Routerinfo() {{
			this._routeLabel = routeLabel;
			this._scenePath = scenePath;
//			this._viewClass = viewClass;
			this._controllerClass = controllerClass;
			this._navName = role;
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

	public static void addNav(String role, URL scenePath) {
		navRoutes.put(role, new Routerinfo() {{
			this._routeLabel = role;
			this._scenePath = scenePath;
		}});
	}

	public static Object goTo(String routeLabel) {
		if(currentRouteLabel.equals(routeLabel)) {
			return null;
		}

		Routerinfo route = routes.get(routeLabel);
		if (route == null) {
			throw new RuntimeException("Route not found");
		}

		try {
			var controller = loadNewSubScene(route);
			currentRouteLabel = routeLabel;
			return controller;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void showWindow() {
		var userServ = UserService.getInstance();
		var user = userServ.getCurrentUser();
		onUserChanged(userServ, user);
		UserService.getInstance().onUserChanged(FXRouter::onUserChanged);
		window.show();
	}

	private static INavBar loadNewScene(Routerinfo route) throws Exception{
		var loader = new FXMLLoader(route._scenePath);

		Parent resource = loader.load();
		var view = loader.getController();

		if(route._controllerClass != null) {
			route._controllerClass.getDeclaredConstructor(view.getClass()).newInstance(view);
		}

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

//		var scene = new Scene(resource);
//		window.setScene(scene);
//		window.show();

		currentNav.loadPage(route._routeLabel, resource);

		return controller;
	}

	private static void onUserChanged(UserService service, User user) {
		if(user == null) {
			try {
				loadNewScene(loginRoute);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return;
		}

		var navRoute = navRoutes.get(user.getRole());
		if(navRoute != null) {
			try {
                currentNav = loadNewScene(navRoute);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		else {
			throw new RuntimeException("Role " + user.getRole() + " not have nav route");
		}

        currentNav.navigateToHome();
	}

}
