package fu.learning.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fu.learning.springboot.entity.Employee;
import fu.learning.springboot.service.EmployeeService;

@Controller
public class EmployeeController {
	private EmployeeService employeeService; 
	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	@GetMapping("/employees")
	public String showEmployeePage(@RequestParam(name = "index", required = false) Integer index, 
									@RequestParam(name = "size", required = false) Integer size) { 
		//Call service 
		Page<Employee> page =  employeeService.findAll(index, size);  
		return "employees"; // return the view name
	}
	@GetMapping("/employee/add")
	public String showAddEmployeePage() {
		return "add-employee"; // return the view name for adding an employee
	}
	
}
