package controllers;

import java.util.List;

import models.BuyStock;
import models.Stock;
import models.User;
import play.data.validation.Min;
import play.data.validation.Required;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import sun.util.logging.resources.logging;

public class Buy extends Application {
	

	public static void buy() {
		render();
	}
	
	public static void confirmBuy(@Required String code, @Required @Min(1) Integer amount) {
		if(validation.hasErrors()) {
			validation.keep();
			params.flash();
			flash.error("Please correct errors !");
			buy();
		}
		Stock stock = Stock.findByCode(code);
		//notFoundIfNull(stock);
		if(stock == null) {
			params.flash();
			flash.error("This Stock Code not exist, Pls confirm!");
			buy();			
		}
		User user = User.findById(Long.valueOf(session.get("user")));
		BuyStock buyStock = new BuyStock(stock, user, amount);
		buyStock.save();
		list();
	}

	public static void list() {
		User user = User.findById(Long.valueOf(session.get("user")));
		List<BuyStock> buys = user.findBuyStocks();
		System.out.println(buys.size());
		render(buys);
	}
	
	
	public static void listAllStock(@Required String q) {
		List<Stock> stocks = Stock.findLikeCode(q);
		System.out.println("q: " + q);
		render(stocks);
	}
}
