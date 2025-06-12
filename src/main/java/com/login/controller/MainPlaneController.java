package com.login.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.login.config.JwtConfig;
import com.login.feignClient.WorkTimeFeignClient;
import com.login.model.Worker;
import com.login.service.main.MainPlaneServiceInterface;
import com.login.service.rabbitmq.MessageSender;
import com.shared_dto.mode.WorkTime;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainPlaneController {
	
	@Autowired
	private MainPlaneServiceInterface service;
	
	@Autowired
	private WorkTimeFeignClient client;
	
	@Autowired
	private MessageSender sender;
	
	@Autowired
	JwtConfig jwt;
	
	@GetMapping("/main")
	public String mainHTML() {
		
		return "main";
	}
	
	@PostMapping("/api/validateToken")
	public ResponseEntity<Boolean> validateToken(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
	    if (cookies == null) {
	    	System.out.print("没有cookie\n");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    String token = Arrays.stream(cookies)
	            			 .filter(c -> "userClientToken".equals(c.getName()))
	            			 .map(Cookie::getValue)
	            			 .findFirst()
	            			 .orElse(null);
	    System.out.print("cookie验证\n");
	    if (token == null || !jwt.validateToken(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    System.out.print("token有效\n");
		return ResponseEntity.ok().build();
	}
	
	/**
	 * 
	 * @param null
	 * @return 员工列表
	 */
	@PostMapping("/api/updateEmployees")
	public ResponseEntity<Map<String,Object>> updateEmployees(@RequestBody List<Worker> employees) {
		boolean res = service.updateEmployees(employees);
		System.out.print("name: "+employees.get(0).getName());
		Map<String,Object> response = new HashMap<>();
		response.put("message", res);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * 
	 * @param name
	 * @return 指定员工
	 */
	@GetMapping("/api/employees")
	@ResponseBody
	@SentinelResource(value = "getAllWorkerInfo",blockHandler = "flowService")
	public ResponseEntity<List<Worker>> getEmployees(@RequestParam(required=false) String name){
		List<Worker> employees = null;
		if(name==null || name.equals(""))
			employees = service.selectAllEmployees();
		else
			employees = service.getEmployees(name);
		
		return ResponseEntity.ok(employees);
	}
	
	public void flowService() {
		System.out.println("应用已限流");
	}
	
	/**
	 * 
	 * @param name
	 * @return 考勤表列表
	 */
	@GetMapping("/getWorkTimeInfo")
	@ResponseBody
	public ResponseEntity<List<WorkTime>> getWorkTimeInfo(@RequestParam(required = false) String name) {
        // 调用Feign客户端获取考勤数据
        List<WorkTime> workTimeList = client.getWorkTimeInfo(name);
        return ResponseEntity.ok(workTimeList);
    }
}
