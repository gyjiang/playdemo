package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.JPAModel;

@Entity
public class BuyStock extends JPAModel {

	@ManyToOne public Stock stock;
	@ManyToOne public User user;
	public BigDecimal buyPrice;
	public Integer amount;
	
	public BuyStock(Stock stock, User user, int amount) {
		this.stock = stock;
		this.user = user;
		this.buyPrice = stock.price;
		this.amount = amount;
	}

}
