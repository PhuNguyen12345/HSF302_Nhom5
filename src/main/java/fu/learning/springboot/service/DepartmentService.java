package fu.learning.springboot.service;

import fu.learning.springboot.entity.Department;

public interface DepartmentService {
	Department getDepartmentById(Long id); 
	Department addDepartment(Department department); 
}
