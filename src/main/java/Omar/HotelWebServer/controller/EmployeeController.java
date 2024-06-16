package Omar.HotelWebServer.controller;

import Omar.HotelWebServer.dataAccess.model.classes.Employee;
import Omar.HotelWebServer.dataAccess.model.enums.EmployeePosition;
import Omar.HotelWebServer.service.EmployeeService;
import Omar.HotelWebServer.utils.exceptions.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee( HttpServletRequest request ,@RequestBody Employee employee) {
        if(!isAdmin(request)) {
            throw new NotAuthorizedException("You do not have permission to add an employee.");
        }

        Employee addedEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedEmployee);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(HttpServletRequest request,@PathVariable Integer employeeId) {
        if(!isAdmin(request)) {
            throw new NotAuthorizedException("You do not have permission to delete an employee.");
        }

        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees(HttpServletRequest request) {
        if(!isAdmin(request)) {
            throw new NotAuthorizedException("You do not have permission to view all employees.");
        }

        return employeeService.getAllEmployees();
    }

    private boolean isAdmin(HttpServletRequest request) {
        if(request.isUserInRole("ROLE_USER")) {
            return false;
        }
        return true;
    }

}
