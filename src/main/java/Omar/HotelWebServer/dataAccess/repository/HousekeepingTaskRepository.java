package Omar.HotelWebServer.dataAccess.repository;

import Omar.HotelWebServer.dataAccess.model.classes.Employee;
import Omar.HotelWebServer.dataAccess.model.classes.HousekeepingTask;
import Omar.HotelWebServer.dataAccess.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HousekeepingTaskRepository extends JpaRepository<HousekeepingTask, Integer> {

    List<HousekeepingTask> findByTaskStatus(TaskStatus taskStatus);

    List<HousekeepingTask> findByAssignedEmployeesContaining(Employee employee);

    @Query("SELECT COUNT(ht) FROM HousekeepingTask ht JOIN ht.assignedEmployees he WHERE he.id = :employeeId AND ht.taskStatus <> 'FINISHED'")
    int countByAssignedEmployeesAndNotFinished(@Param("employeeId") Integer employeeId);
}
