package controllers;

import models.User;
import play.Logger;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {
	
	@Before(unless={"index", "login", "signup", "register", "auth", "logout"})
	static void checkLogin() {
		if(session.get("user") == null) {
			flash.error("Please Login!");
			Logger.debug("forward to Login");
			Application.login();
		}
	}

	public static void index() {
		render();
	}

	public static void signup() {
		render();
	}
	
	public static void register(@Required @Email String email, @Required @MinSize(6) String password, @Equals("password") String password2, @Required String name) {
		if(validation.hasErrors()) {
			validation.keep();
			params.flash();
			flash.error("Please correct these errors !");
			signup();
		}
		User user = new User(email, password, name);
		user.save();
		
		login();
	}

	public static void login() {
		render();
	}

	public static void auth(String email, String password) {
		User user = User.findByEmail(email);
		if (user == null || !user.checkPassword(password)) {
			flash.error("Bad email or bad password");
			flash.put("email", email);
			login();
		}
		session.put("user", user.id);
		session.put("username", user.name);
		flash.success("Welcome back %s !", user.name);
		index();

	}

	
	public static void logout() {
		flash.success("You've logged out!");
		session.clear();
		login();
	}

}