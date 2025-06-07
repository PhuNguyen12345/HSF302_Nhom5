package fu.learning.springboot.service;

import java.math.BigDecimal;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fu.learning.springboot.dto.EmployeeDTO;
import fu.learning.springboot.entity.Employee;
import fu.learning.springboot.exception.IllegalResourceException;
import fu.learning.springboot.repository.EmployeeRepository;

@Service // IoC
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired // DI: Field Injection
	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee save(Employee e) {
		// TODO Auto-generated method stub
		//validate/Convert data/ Check business rule 
		//Check if e is null 
		if (e == null) {
			throw new IllegalResourceException("Employee is null"); 
		}
		if (e.getSalary().compareTo(new BigDecimal(-2000000)) < 0) {
			throw new IllegalResourceException("Salary must be higher than -2000000"); 
		}
		return employeeRepository.save(e);
	}

	@Override
	public Employee delete(Employee employee) {
		// TODO Auto-generated method stub
		return employeeRepository.save(employee); 
	}

	@Override
	public List<EmployeeDTO> findMultipleTables() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public void updateStatus(String id) {
		// TODO Auto-generated method stub
		employeeRepository.updateStatus(id); 
	}

	@Override
	public Employee findById(String id) {
		// TODO Auto-generated method stub
		Optional<Employee> employee = employeeRepository.findById(id); 
		//Check 
		if (employee.isPresent()) {
			return employee.get(); 
		}
		return new Employee(); 
 	}

	@Override
	public Page findAll(Integer pageIndex, Integer pageSize) {
		if (pageIndex == null || pageIndex < 0) {
			throw new IllegalResourceException("Page index must be greater than or equal to 0");
		}
		Sort sort = Sort.by(Sort.Order.asc("salary")); 
		Pageable pageable = PageRequest.of(pageIndex, pageSize,sort);
		return employeeRepository.findAll(pageable); //only 
	}
}
