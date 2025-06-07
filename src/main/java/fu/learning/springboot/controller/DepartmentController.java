package fu.learning.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.learning.springboot.entity.Department;
import fu.learning.springboot.service.DepartmentService;

@RestController
@RequestMapping("/api")
public class DepartmentController {
	private DepartmentService departmentService;

	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping({ "/departments" })
	public Map<String, Department> getDepartments() {
		Map<String, Department> map = new HashMap<>();
		// Call service
		Department found = departmentService.getDepartmentById(1L);
		// return
		map.put("result", found);

		return map;
	}
}
