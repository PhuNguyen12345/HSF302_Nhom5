package fu.learning.springboot.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(schema = "hr") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dependence {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @OneToOne
    @JoinColumn(name = "emp_id", referencedColumnName = "id", unique = true)
    private Employee employee;
}
