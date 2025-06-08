package com.login.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.login.service.login.LoginServiceInterface;
import com.login.utils.JwtConfig;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author zyh
 * @Date 2025/5/19
 */

@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginHTML() {

		return "login";
	}

	@Autowired
	LoginServiceInterface loginService;

	@Autowired
	JwtConfig jwt;

	/**
	 * 
	 * @param 管理员姓名和登录密码
	 * @return 登录结果
	 */
	@PostMapping("/api/managerLogin")
	@Operation(summary = "login", description = "根据账号密码验证管理员身份")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> requestBody) {
		String account = requestBody.get("account");
		String password = requestBody.get("password");

		// 登录验证
		Map<String, Object> response = loginService.login(account, password);
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * @param name
	 * @param password
	 * @return 登录结果
	 */
	/*
	 * @PostMapping("/api/login")
	 * 
	 * @Operation(summary = "userLogin", description = "根据账号密码验证员工身份") public
	 * ResponseEntity<Map<String, Object>> userLogin(HttpServletResponse
	 * response, @RequestBody Worker worker) { Map<String, Object> res = new
	 * HashMap<>();
	 * 
	 * try { Worker ret = loginService.userLogin(worker.getName(),
	 * worker.getPassword()); if (ret != null) { // 登录成功
	 * 
	 * String userId = ret.getPid() + "";
	 * 
	 * String token = jwt.generateToken(userId); // 设置HttpOnly Cookie ResponseCookie
	 * cookie = ResponseCookie.from("userClientToken", token) .httpOnly(true)
	 * .secure(false) // 开发环境允许 .path("/") // 全局路径有效 //.sameSite("None") // 允许跨站点携带
	 * Cookie .maxAge(8640).build(); response.addHeader(HttpHeaders.SET_COOKIE,
	 * cookie.toString()); res.put("code", 200); res.put("message", "登录成功");
	 * res.put("token", token); res.put("data", Map.of("userId", ret.getPid(),
	 * "username", ret.getName())); return ResponseEntity.ok(res); } else { //
	 * 用户名或密码错误 res.put("code", 401); res.put("message", "用户名或密码错误"); return
	 * ResponseEntity.status(401).body(res); } } catch (Exception e) { // 服务器内部错误
	 * res.put("code", 500); res.put("message", "服务器异常：" + e.getMessage());
	 * System.out.print("message" + "服务器异常：" + e.getMessage()); return
	 * ResponseEntity.status(500).body(res); } }
	 */
}