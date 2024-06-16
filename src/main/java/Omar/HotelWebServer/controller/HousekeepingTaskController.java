package Omar.HotelWebServer.controller;

import Omar.HotelWebServer.dataAccess.model.classes.HousekeepingTask;
import Omar.HotelWebServer.service.EmployeeService;
import Omar.HotelWebServer.service.HousekeepingTaskService;
import Omar.HotelWebServer.utils.exceptions.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class HousekeepingTaskController {

    private final HousekeepingTaskService taskService;
    private final EmployeeService employeeService;

    @Autowired
    public HousekeepingTaskController(HousekeepingTaskService taskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.employeeService = employeeService;
    }

    // 1. Get all tasks
    @GetMapping("/all")
    public List<HousekeepingTask> getAllTasks() {
        return taskService.getAllTasks();
    }

    // 2. Assign specific room to a specific employee
    @PostMapping("/assign/{roomId}/employee/{employeeId}")
    public ResponseEntity<String> assignRoomToEmployee(HttpServletRequest request,
            @PathVariable Integer roomId,
            @PathVariable Integer employeeId) {
        if(!isAdmin(request)) {
            throw new NotAuthorizedException("You do not have permission to assign tasks.");
        }
        taskService.assignRoomToEmployee(roomId, employeeId);
        return ResponseEntity.ok("Room assigned to employee successfully");
    }

    // 3. Assign all rooms with status NEEDS_CLEANING to employees, balancing tasks
    @PostMapping("/assign-all")
    public ResponseEntity<String> assignAllRoomsToEmployees(HttpServletRequest request) {
        if(!isAdmin(request)) {
            throw new NotAuthorizedException("You do not have permission to assign tasks.");
        }
        taskService.assignAllRoomsToEmployees();
        return ResponseEntity.ok("Rooms assigned to employees successfully");
    }

    // 4. Get tasks of specific employee
    @GetMapping("/employee/{employeeId}")
    public List<HousekeepingTask> getTasksByEmployee(HttpServletRequest request,@PathVariable Integer employeeId) {
        if(!isAdmin(request)) {
            throw new NotAuthorizedException("You do not have permission to see employee tasks.");
        }

        return taskService.getTasksByEmployee(employeeId);
    }

    // 5. Admin changes specific task status to FINISHED
    @PutMapping("/{taskId}/finish")
    public ResponseEntity<String> finishTask(HttpServletRequest request ,@PathVariable Integer taskId) {
        if(!isAdmin(request)) {
            throw new NotAuthorizedException("You do not have permission to finish a task.");
        }
        taskService.finishTask(taskId);
        return ResponseEntity.ok("Task marked as finished successfully");
    }

    private boolean isAdmin(HttpServletRequest request) {
        if(request.isUserInRole("ROLE_USER")) {
            return false;
        }
        return true;
    }

}
