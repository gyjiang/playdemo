package controllers;

import java.math.BigDecimal;
import java.util.List;

import models.Stock;

import play.data.validation.Min;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class StockMani extends Application  {

	public static void listStock() {
		List<Stock> stocks = Stock.findAll(1, 20);
		System.out.println(stocks.size());
		render(stocks);
	}
	
	public static void addStock() {
		render();
	}
	
	public static void saveStock(@Required String code, @Required String name, @Required @Min(0) BigDecimal price) {
		if(validation.hasErrors()) {
			validation.keep();
			params.flash();
			flash.error("Please correct errors !");
			addStock();
		}
		Stock stock = new Stock(code, name, price);
		stock.save();
		listStock();
	}
}
