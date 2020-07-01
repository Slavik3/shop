package com.ukrposhta.shop.dao;

import java.util.List;

import com.ukrposhta.shop.entity.ClientAccount;
import com.ukrposhta.shop.entity.Product;

public interface ProductDao {
	List<Product> listProducts();

	List<Product> listProducts(String category);
	
	Product get(int id);

	void buy(ClientAccount clientAccount);

}
