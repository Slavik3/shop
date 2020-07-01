package com.ukrposhta.shop.controller;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ukrposhta.shop.entity.ClientAccount;
import com.ukrposhta.shop.entity.Product;
import com.ukrposhta.shop.exception.NotEnothMoneyException;
import com.ukrposhta.shop.service.ProductService;
import com.ukrposhta.shop.service.UserService;

@RestController
public class Controller {

	@Autowired
	UserService userService;
	@Autowired
	ProductService productService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProducts(
			@RequestParam(value = "category", required = false) String category) {
		if (category != null) {
			Iterable<Product> allProducts = productService.listProducts(category);
			return new ResponseEntity<>(allProducts, HttpStatus.OK);
		} else {
			Iterable<Product> allProducts = productService.listProducts();
			return new ResponseEntity<>(allProducts, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/create_user", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody ClientAccount clientAccount) {
		clientAccount = userService.save(clientAccount);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(clientAccount.getId()).toUri());
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/add_money", method = RequestMethod.PUT)
	public ResponseEntity<?> addMoney(@RequestParam("id") Long id, @RequestParam("money") BigDecimal money) {
		ClientAccount clientAccount = userService.getClientAccount(id);
		BigDecimal clientMoney = clientAccount.getAmountOfMoney().add(money);
		clientAccount.setAmountOfMoney(clientMoney);
		userService.updateClientAccount(clientAccount);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/pay", method = RequestMethod.PUT)
	public ResponseEntity<?> pay(@RequestParam("productId") int productId[],
			@RequestParam("clientAccountId") Long clientAccountId) throws NotEnothMoneyException {
		productService.buy(productId, clientAccountId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<ClientAccount> addSource() throws URISyntaxException {
		return userService.listUsers();
	}

}
