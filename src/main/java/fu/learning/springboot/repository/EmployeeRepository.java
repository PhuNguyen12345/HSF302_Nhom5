package fu.learning.springboot.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import fu.learning.springboot.dto.EmployeeDTO;
import fu.learning.springboot.entity.Employee;
import jakarta.transaction.Transactional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	List<Employee> findByLastNameEndingWithIgnoreCase(String name); 
	List<Employee> findByHireDateBefore(LocalDate date);
	List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary); 
	@Transactional
	@Modifying 
	@Query("UPDATE Employee e SET isActive = false WHERE e.id = ?1") //default: value = ""
	void updateStatus(String id);  
	
//	@Query(name = "findMultipleTables") 
	List<EmployeeDTO> findMultipleTables();    
}
