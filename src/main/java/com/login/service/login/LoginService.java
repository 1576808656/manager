package com.login.service.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.mapper.SqlMapper;
import com.login.model.Manager;
import com.login.model.Worker;

@Service
public class LoginService implements LoginServiceInterface {

	@Autowired
	SqlMapper sql;
	
	@Override
	public Map<String,Object> login(String account, String password) {
		Manager manager = sql.login(account, password);
		boolean res = (manager != null);
		Map<String, Object> response = new HashMap<>();
		response.put("success", res);
		if(res) {
			response.put("message", "登录成功");
		}
		else
			response.put("message", "登录失败");
		return response;
	}

	@Override
	public Worker userLogin(String name, String password) {
		Worker worker = sql.userLogin(name, password);
		
		return worker;
	}

}
