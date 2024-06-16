package Omar.HotelWebServer.dataAccess.repository;

import Omar.HotelWebServer.dataAccess.model.classes.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
