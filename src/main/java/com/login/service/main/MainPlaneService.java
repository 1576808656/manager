package com.login.service.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.mapper.SqlMapper;
import com.login.model.Worker;

@Service
public class MainPlaneService implements MainPlaneServiceInterface {

	@Autowired
	SqlMapper mapper;
	
	@Override
	public List<Worker> getEmployees(String name) {
		List<Worker> res = mapper.getEmployees(name);
		return res;
	}

	@Override
	public List<Worker> selectAllEmployees() {

		return mapper.selectAllEmployees();
	}

	@Override
	public boolean updateEmployees(List<Worker> list) {
		int bool=0;
		for(int i=0;i<list.size();i++) {
			Worker worker = list.get(i);
			worker.setPhoto(worker.getPid()+"_"+worker.getName());
			bool = mapper.updateEmployees(worker);
		}
		if(bool==0)
			return false;
		return true;
	}

}
