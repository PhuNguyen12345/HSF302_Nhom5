package fu.learning.springboot.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*; 

@Entity 
@Table(schema = "hr", name = "employee") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedQuery(name = "Employee.findMultipleTables", query = "SELECT e.id, e.firstName, "
		+ "e.lastName, e.hireDate, j.jobTitle FROM Employee e JOIN e.job j")
public class Employee {
	@Id
    private String id;

    @Column(name = "first_name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(name = "phone)number", unique = true)
    private String phoneNumber;

    @Column(name ="hire_date")
    private LocalDate hireDate;

    @Column(columnDefinition = "DECIMAL", precision = 12, scale = 2)
    private BigDecimal salary;

    @Column(name = "commission_pct")
    private BigDecimal commissionPct;
    
    @Column(name = "is_active") 
    public boolean isActive; 

    @ManyToOne
    @JoinColumn(name = "job_id",
            referencedColumnName = "id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Employee employee;

    @OneToOne(mappedBy = "employee")
    private Dependence dependence;

    @OneToOne(mappedBy = "manager")
    private Department deptManagement;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @OneToMany(mappedBy = "ids.employee", cascade = CascadeType.ALL)
    private Set<JobHistory> jobHistories;
}
