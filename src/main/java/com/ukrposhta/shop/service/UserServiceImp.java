package com.ukrposhta.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ukrposhta.shop.dao.UserDao;
import com.ukrposhta.shop.entity.ClientAccount;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	@Override
	public List<ClientAccount> listUsers() {
		return userDao.listUsers();
	}

	@Transactional
	@Override
	public ClientAccount save(ClientAccount clientAccount) {
		userDao.save(clientAccount);
		return clientAccount;
	}

	@Override
	public ClientAccount getClientAccount(Long id) {
		return userDao.getClientAccount(id);
	}

	@Override
	public void updateClientAccount(ClientAccount clientAccount) {
		userDao.updateClientAccount(clientAccount);	
	}

	

}
