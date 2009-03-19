package models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;

import play.cache.Cache;
import play.db.jpa.JPAModel;

@Entity
public class Stock  extends JPAModel {
	
	public String code;
	public String name;
	public BigDecimal price;
	
	public Stock(String code, String name, BigDecimal price) {
		this.code = code;
		this.name = name;
		this.price = price;
	}
	
	public static Stock findByCode(String code) {
		Stock stock = Cache.get("stock_" + code, Stock.class);
		if(stock == null) {
			stock = find("code", code).one();
			if(stock != null) {
				Cache.safeSet("stock_" + code, stock, "30mn");
			}
		}
		return  stock;
	}

	public static Stock findByName(String name) {
		return find("name", name).one();
	}

	public static List<Stock> findAll(int page, int pageSize) {
		return Stock.find().page(page, pageSize);
	}
	
	public static List<Stock> findLikeCode(String lcode) {
		return Stock.findBy("code like ?", lcode + "%");
	}

}
