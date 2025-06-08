package com.login.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shared_dto.mode.WorkTime;

@FeignClient(name = "workTime")
public interface WorkTimeFeignClient {

	@GetMapping("/getWorkTimeInfo")
	public List<WorkTime> getWorkTimeInfo(@RequestParam("name")String name);
}
