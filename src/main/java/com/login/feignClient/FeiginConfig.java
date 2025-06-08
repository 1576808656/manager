package com.login.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.shared_dto.mode.Wage;

@FeignClient(name = "wage-service")
public interface FeiginConfig {

	@GetMapping("/getAllWageInfo")
	public List<Wage> getAllWageInfo();
	
}
