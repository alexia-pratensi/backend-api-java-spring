package fr.alexia.backendapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/me")
public class UserController {

	@GetMapping("hello")
	public String getHelloUser() {
		return "Hello User";
	}
}
 