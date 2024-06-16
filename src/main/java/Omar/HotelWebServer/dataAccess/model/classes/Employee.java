package Omar.HotelWebServer.dataAccess.model.classes;

import Omar.HotelWebServer.dataAccess.model.enums.EmployeePosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeePosition position;

    private int numTasks = 0 ; // Track number of assigned tasks

    public int getNumTasks() {
        return numTasks;
    }

    public void incrementNumTasks() {
        this.numTasks++;
    }

    // Getter and setters for other fields
}
