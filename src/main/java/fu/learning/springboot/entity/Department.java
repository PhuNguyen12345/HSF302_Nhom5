package fu.learning.springboot.entity;

import java.util.Set;

import jakarta.persistence.*; 
import lombok.*;

@Entity
@Table(schema = "hr", name = "department") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id", unique = true)
    private Employee manager;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Employee> employees;

    @OneToMany(mappedBy = "department")
    private Set<JobHistory> jobHistories;
}
