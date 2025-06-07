package fu.learning.springboot.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "hr", name = "jobHistory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobHistory {

	@EmbeddedId
	private JobHistoryIds ids;

	@Column(name = "end_date")
	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(name = "job_id", referencedColumnName = "id")
	private Job job;

	@ManyToOne
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Department department;

}
