package fu.learning.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import fu.learning.springboot.dto.EmployeeDTO;
import fu.learning.springboot.entity.Employee;

public interface EmployeeService {
	//save
	Employee save(Employee e); 
	//delete 
	Employee delete(Employee employee); 
	//find
	List<EmployeeDTO> findMultipleTables();  
	//update 
	void updateStatus(String id); 
	//find by id 
	Employee findById(String id); 
	//Pageable
	Page<Employee> findAll(Integer pageIndex, Integer pageSize); 
}
