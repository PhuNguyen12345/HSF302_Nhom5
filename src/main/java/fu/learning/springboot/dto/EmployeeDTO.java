package fu.learning.springboot.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
	private String id; 
	private String firstName; 
	private String lastName; 
	private LocalDate hireDate; 
	private String jobTitle; 
}
