package Omar.HotelWebServer.dataAccess.model.classes;


import Omar.HotelWebServer.dataAccess.model.enums.EmployeePosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private EmployeePosition position;

}
