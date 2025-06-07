package fu.learning.springboot.entity;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.*; 
import lombok.*; 

@Entity 
@Table(name = "job", schema = "hr") 
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter 
//@ToString(exclude = {"employees", "jobHistories"})
@EqualsAndHashCode
public class Job {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String jobTitle; // camelCase

    @Column(name = "min_salary", columnDefinition = "DECIMAL", precision = 14, scale = 2) // 999 999 999 999.99
    private BigDecimal minSalary;

    @Column(name = "max_salary", columnDefinition = "DECIMAL", precision = 14, scale = 2)
    private BigDecimal maxSalary;
    
    @OneToMany(mappedBy = "job")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "job")
    private Set<JobHistory> jobHistories;
}
