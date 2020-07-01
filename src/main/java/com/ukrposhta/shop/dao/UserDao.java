package com.ukrposhta.shop.dao;

import java.util.List;

import com.ukrposhta.shop.entity.ClientAccount;

public interface UserDao {
	//void add(ClientAccount user);

	List<ClientAccount> listUsers();

	void save(ClientAccount clientAccount);
	
	ClientAccount getClientAccount(Long id);

	void updateClientAccount(ClientAccount clientAccount);
	
	
}
