package Omar.HotelWebServer.dataAccess.model.classes;

import Omar.HotelWebServer.dataAccess.model.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "housekeeping_tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HousekeepingTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "task_date")
    private LocalDate taskDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull
    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @ManyToMany
    @JoinTable(
            name = "housekeeping_employee_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> assignedEmployees;

    public Set<Employee> getAssignedEmployees() {
        if (assignedEmployees == null) {
            assignedEmployees = new HashSet<>();
        }
        return assignedEmployees;
    }

    // Other getters and setters
}
