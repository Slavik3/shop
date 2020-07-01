package com.ukrposhta.shop.service;

import java.util.List;

import com.ukrposhta.shop.entity.ClientAccount;

public interface UserService {
    //void add(ClientAccount user);
    List<ClientAccount> listUsers();
	ClientAccount save(ClientAccount clientAccount);
	ClientAccount getClientAccount(Long id);
	void updateClientAccount(ClientAccount clientAccount);
	
}
