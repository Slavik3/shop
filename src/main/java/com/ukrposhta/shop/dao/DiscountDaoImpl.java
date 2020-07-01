package com.ukrposhta.shop.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ukrposhta.shop.entity.Discount;

@Transactional
@Repository
public class DiscountDaoImpl implements DiscountDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Discount> get() {
		@SuppressWarnings("unchecked")
		TypedQuery<Discount> query = sessionFactory.getCurrentSession().createQuery("from Discount");
		return query.getResultList();
	}

}
