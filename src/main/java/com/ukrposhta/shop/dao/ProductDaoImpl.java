package com.ukrposhta.shop.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ukrposhta.shop.entity.ClientAccount;
import com.ukrposhta.shop.entity.Product;

@Transactional
@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Product> listProducts() {
		@SuppressWarnings("unchecked")
		TypedQuery<Product> query = sessionFactory.getCurrentSession().createQuery("from Product");
		return query.getResultList();
	}

	@Override
	public List<Product> listProducts(String category) {
		@SuppressWarnings("unchecked")
		TypedQuery<Product> query = sessionFactory.getCurrentSession()
				.createQuery("from Product WHERE category_id = (SELECT id FROM Сategory where name='phones')");
		return query.getResultList();
	}

	@Override
	public Product get(int id) {
		return sessionFactory.getCurrentSession().find(Product.class, id);
	}

	@Override
	public void buy(ClientAccount clientAccount) {
		sessionFactory.getCurrentSession().update(clientAccount);
	}

}
