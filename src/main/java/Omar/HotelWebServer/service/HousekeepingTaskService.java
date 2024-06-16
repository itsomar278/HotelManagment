package Omar.HotelWebServer.service;

import Omar.HotelWebServer.dataAccess.model.classes.Employee;
import Omar.HotelWebServer.dataAccess.model.classes.HousekeepingTask;
import Omar.HotelWebServer.dataAccess.model.classes.Room;
import Omar.HotelWebServer.dataAccess.model.enums.RoomStatus;
import Omar.HotelWebServer.dataAccess.model.enums.TaskStatus;
import Omar.HotelWebServer.dataAccess.repository.EmployeeRepository;
import Omar.HotelWebServer.dataAccess.repository.HousekeepingTaskRepository;
import Omar.HotelWebServer.dataAccess.repository.RoomRepository;
import Omar.HotelWebServer.utils.exceptions.EmptyResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

@Service
public class HousekeepingTaskService {

    private final HousekeepingTaskRepository taskRepository;
    private final EmployeeService employeeService;
    private final RoomRepository roomRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public HousekeepingTaskService(HousekeepingTaskRepository taskRepository,
                                   EmployeeService employeeService,
                                   RoomRepository roomRepository,
                                   EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeService = employeeService;
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<HousekeepingTask> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public void assignRoomToEmployee(Integer roomId, Integer employeeId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Room room = roomOptional.orElseThrow(() -> new EmptyResultException("Room not found with id: " + roomId));

        if (!room.getStatus().equals(RoomStatus.NEEDS_CLEANING)) {
            throw new EmptyResultException("Room is not in NEEDS_CLEANING status");
        }

        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            throw new EmptyResultException("Employee not found with id: " + employeeId);
        }

        HousekeepingTask newTask = createHousekeepingTask(room, employee);
        taskRepository.save(newTask);
    }

    @Transactional
    public void assignAllRoomsToEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<Room> rooms = roomRepository.findByStatus(RoomStatus.NEEDS_CLEANING);

        PriorityQueue<Employee> employeeQueue = new PriorityQueue<>(Comparator.comparingInt(this::countAssignedTasks));
        employeeQueue.addAll(employees);

        for (Room room : rooms) {
            if (employeeQueue.isEmpty()) {
                break;
            }

            Employee nextEmployee = employeeQueue.poll();
            HousekeepingTask task = createHousekeepingTask(room, nextEmployee);
            taskRepository.save(task);

            nextEmployee.incrementNumTasks();
            employeeRepository.save(nextEmployee);

            employeeQueue.offer(nextEmployee);
        }
    }

    private HousekeepingTask createHousekeepingTask(Room room, Employee employee) {
        HousekeepingTask task = new HousekeepingTask();
        task.setTaskDate(LocalDate.now());
        task.setRoom(room);
        task.setTaskStatus(TaskStatus.ASSIGNED);
        task.getAssignedEmployees().add(employee);
        return task;
    }

    private int countAssignedTasks(Employee employee) {
        return taskRepository.countByAssignedEmployeesAndNotFinished(employee.getId());
    }

    @Transactional
    public void finishTask(Integer taskId) {
        HousekeepingTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.setTaskStatus(TaskStatus.FINISHED); // Fixed casing for TaskStatus
        taskRepository.save(task);
    }

    public List<HousekeepingTask> getTasksByEmployee(Integer employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return taskRepository.findByAssignedEmployeesContaining(employee);
    }
}
