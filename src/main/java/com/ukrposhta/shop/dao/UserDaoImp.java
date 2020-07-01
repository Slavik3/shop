package com.ukrposhta.shop.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ukrposhta.shop.entity.ClientAccount;

@Transactional
@Repository
public class UserDaoImp implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<ClientAccount> listUsers() {
		@SuppressWarnings("unchecked")
		TypedQuery<ClientAccount> query = sessionFactory.getCurrentSession().createQuery("from ClientAccount");
		return query.getResultList();
	}

	@Override
	public void save(ClientAccount clientAccount) {
		sessionFactory.getCurrentSession().save(clientAccount);

	}

	@Override
	public ClientAccount getClientAccount(Long id) {
		return sessionFactory.getCurrentSession().find(ClientAccount.class, id);
	}

	@Override
	public void updateClientAccount(ClientAccount clientAccount) {
		sessionFactory.getCurrentSession().update(clientAccount);
	}

}
