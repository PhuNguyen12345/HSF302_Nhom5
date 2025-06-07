package fu.learning.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.learning.springboot.entity.Employee;
import fu.learning.springboot.service.EmployeeService;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
	private EmployeeService employeeService; 
	
	//DI 
	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService; 
	}
	
	//API Definition
	//@RequestBody to receive response employee
	//API's param = bean 
	@PostMapping("/employees") //method = "POST" 
	//@GetMapping /method = "GET" 
	//C1: AddEmloyee(Update by overwrite data with same data)
//	public Employee addEmployee(@RequestBody Employee employee) {
//		return employeeService.delete(employee); //using jackson to map from JSON (String) -> Object 
//	}
	//C2: update using updateMethod 
	public Employee delete(@RequestBody Employee employee) {
		employeeService.updateStatus(employee.getId());
		//return 
		return employeeService.findById(employee.getId());  
	}
	
	//c2: chay ham update truc tiep 
}
