package models;

import play.*;
import play.cache.Cache;
import play.db.jpa.*;
import javax.persistence.*;
import java.util.*;
import play.libs.*;

@Entity
public class User extends JPAModel {
	
	public String email;
	public String passwordHash;
	public String name;	

	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="user") public List<BuyStock> stocks;
	
	public User(String email, String password, String name) {
		this.email = email;
		this.passwordHash = Codec.hexMD5(password);
		this.name = name;
	}
	
	public boolean checkPassword(String password) {
		return passwordHash.equals(Codec.hexMD5(password));
	}
	
	public static User findByEmail(String email) {
		User user = Cache.get("user_" + email, User.class);
		if(user == null) {
			user = find("email", email).one();
			if(user != null) {
				Cache.safeSet("user_" + email, user, "30mn");
			}
		}
		return user;
	}
	
	public static List<User> findAll(int page, int pageSize) {
		return find().page(page, pageSize);
	}
	
	public List<BuyStock> findBuyStocks() {
		return BuyStock.find("user", this).all();
	}
}
