package fu.learning.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	@GetMapping("/admin") 
	public String showAdminPage() {
		return "admin"; 
	}
}
