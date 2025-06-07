package fu.learning.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.learning.springboot.entity.Department;
import fu.learning.springboot.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	private DepartmentRepository departmentRepository; 
	
	@Autowired
	public void setDepartmentRepository(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository; 
	}

	@Override
	public Department getDepartmentById(Long id) {
		// TODO Auto-generated method stub
		return departmentRepository.findById(id).orElse(null);   
	}

	@Override
	public Department addDepartment(Department department) {
		// TODO Auto-generated method stub
		return departmentRepository.save(department); 
	}
	
}
