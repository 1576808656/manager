package com.login.service.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.login.annotation.SlaveDataSource;
import com.login.mapper.SqlMapper;
import com.login.model.Worker;

@Service
public class MainPlaneService implements MainPlaneServiceInterface {

	@Autowired
	SqlMapper mapper;
	
	@Override
	@SlaveDataSource
	@Cacheable(value = "specialWorker", key = "{#name}")
	@SentinelResource(value = "getWorkerInfo",blockHandler = "")
	public List<Worker> getEmployees(String name) {
		List<Worker> res = mapper.getEmployees(name);
		return res;
	}

	@Override
	@Cacheable(value = "allWorker", key = "{#name}")
	
	public List<Worker> selectAllEmployees() {

		return mapper.selectAllEmployees();
	}

	@Override
	public boolean updateEmployees(List<Worker> list) {
		int bool=0;
		for(int i=0;i<list.size();i++) {
			Worker worker = list.get(i);
			worker.setPhoto(worker.getPid()+"_"+worker.getIdcard());
			bool = mapper.updateEmployees(worker);
		}
		if(bool==0)
			return false;
		return true;
	}

}
