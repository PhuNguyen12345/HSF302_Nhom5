package fu.learning.springboot.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*; 

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JobHistoryIds {
	 @ManyToOne
	    @JoinColumn(name = "employee_id", referencedColumnName = "id")
	    private Employee employee;

	    @Column(name = "start_date")
	    private LocalDate startDate;
}
