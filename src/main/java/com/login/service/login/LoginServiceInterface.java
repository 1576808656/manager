package com.login.service.login;

import java.util.Map;

import com.login.model.Worker;

public interface LoginServiceInterface {
	public Map<String,Object> login(String account, String password);
	
	public Worker userLogin(String name,String password);
}
