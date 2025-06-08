package com.login.service.main;

import java.util.List;

import com.login.model.Worker;

public interface MainPlaneServiceInterface {
	List<Worker> getEmployees(String name);
	List<Worker> selectAllEmployees();
	boolean updateEmployees(List<Worker> list);
}
