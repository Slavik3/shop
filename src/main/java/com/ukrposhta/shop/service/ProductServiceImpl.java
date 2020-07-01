package com.ukrposhta.shop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ukrposhta.shop.dao.DiscountDao;
import com.ukrposhta.shop.dao.ProductDao;
import com.ukrposhta.shop.entity.ClientAccount;
import com.ukrposhta.shop.entity.Discount;
import com.ukrposhta.shop.entity.Product;
import com.ukrposhta.shop.exception.NotEnothMoneyException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DiscountDao discountDao;

	@Transactional(readOnly = true)
	@Override
	public List<Product> listProducts() {
		return productDao.listProducts();
	}

	@Override
	public List<Product> listProducts(String category) {
		return productDao.listProducts(category);
	}

	@Override
	public Product getProduct(int id) {
		return productDao.get(id);
	}

	@Override
	public void buy(int[] productId, Long clientAccountId) throws NotEnothMoneyException {
		List<Product> products = getProducts(productId);
		BigDecimal productsPriceWithDiscount = getTotalPriceOfProducts(products);
		ClientAccount clientAccount = userService.getClientAccount(clientAccountId);
		BigDecimal clientAmountOfMoney = clientAccount.getAmountOfMoney();
		if (clientAmountOfMoney.compareTo(productsPriceWithDiscount)>=0) {
			List<Product> orderedProducts = new ArrayList<Product>();
			orderedProducts = clientAccount.getProducts();
			for (int i = 0; i < products.size(); i++) {
				orderedProducts.add(products.get(i));
			}
			clientAccount.setProducts(orderedProducts);
			BigDecimal curentAmountOfMoney = clientAccount.getAmountOfMoney().subtract(productsPriceWithDiscount);
			clientAccount.setAmountOfMoney(curentAmountOfMoney);
			productDao.buy(clientAccount);
		} else {
			throw new NotEnothMoneyException("not enoth money");
		}
	}
	
	List<Product> getProducts(int productId[]) {
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < productId.length; i++) {
			Product product = getProduct(productId[i]);
			products.add(product);
		}
		return products;
	}
	
	BigDecimal getTotalPriceOfProducts(List<Product> products) {
		BigDecimal productsPrice = new BigDecimal(0);
		List<Discount> discounts = new ArrayList<Discount>();
		discounts = discountDao.get();
		discounts= getTop3Discount(discounts);
		List<Product> discountedGoods = applyDiscountsToGoods(discounts, products);
		for (int i = 0; i < discountedGoods.size(); i++) {
			productsPrice = productsPrice.add(discountedGoods.get(i).getPrice()) ;
		}
		return productsPrice;
	}
	

	List<Discount> getTop3Discount(List<Discount> discounts) {
		return discounts.stream().sorted(Comparator.comparingInt(Discount::getDiscountPercentage).reversed()).limit(3)
				.collect(Collectors.toList());
	}
	
	List<Product> applyDiscountsToGoods(List<Discount> top3Discount, List<Product> products) {
		for (int i = 0; i < top3Discount.size(); i++) {
			int discountPercentage = top3Discount.get(i).getDiscountPercentage();
			BigDecimal productsPrice = top3Discount.get(i).getProduct().getPrice();//DEL
			BigDecimal discountAmountInCurrency = productsPrice.multiply(new BigDecimal(discountPercentage)).divide(new BigDecimal(100));
			BigDecimal newPrice = productsPrice.subtract(discountAmountInCurrency);
			int index =  products.indexOf(top3Discount.get(i).getProduct());
			products.get(index).setPrice(newPrice);
		}
		return products;
	}

}
