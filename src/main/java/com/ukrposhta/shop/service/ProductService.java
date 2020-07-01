package com.ukrposhta.shop.service;

import java.util.List;

import com.ukrposhta.shop.entity.Product;
import com.ukrposhta.shop.exception.NotEnothMoneyException;

public interface ProductService {
	List<Product> listProducts();
	List<Product> listProducts(String category);
	Product getProduct(int id);
	
	void buy(int productId[], Long clientAccountId) throws NotEnothMoneyException;
}
