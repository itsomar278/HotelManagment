package Omar.HotelWebServer.controller;

import Omar.HotelWebServer.dataAccess.model.enums.RoomCapacity;
import Omar.HotelWebServer.dataAccess.model.enums.RoomClass;
import Omar.HotelWebServer.dataAccess.model.enums.RoomStatus;
import Omar.HotelWebServer.service.RoomService;
import Omar.HotelWebServer.utils.DTOs.RoomDTO;
import Omar.HotelWebServer.utils.exceptions.NotAuthorizedException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Integer roomId) {
        RoomDTO roomDTO = roomService.getRoomById(roomId);
        if (roomDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<RoomDTO> addRoom(HttpServletRequest httpServletRequest,@Valid @RequestBody RoomDTO roomDTO) {
        if(!isAdmin(httpServletRequest)) {
            throw new NotAuthorizedException("You do not have permission to add a room.");
        }
        RoomDTO addedRoom = roomService.addRoom(roomDTO);
        return ResponseEntity.ok(addedRoom);
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomDTO> updateRoom(HttpServletRequest httpServletRequest,
            @PathVariable Integer roomId,
            @Valid @RequestBody RoomDTO updatedRoomDTO) {
        if(!isAdmin(httpServletRequest)) {
            throw new NotAuthorizedException("You do not have permission to update a room.");
        }
        updatedRoomDTO.setId(roomId);
        RoomDTO roomDTO = roomService.updateRoom(updatedRoomDTO);
        return ResponseEntity.ok(roomDTO);
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<String> deleteRoom(HttpServletRequest httpServletRequest, @PathVariable Integer roomId) {
        if(!isAdmin(httpServletRequest)) {
            throw new NotAuthorizedException("You do not have permission to delete a room.");
        }
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room deleted successfully");
    }

    @GetMapping("/status/{status}")
    public List<RoomDTO> getRoomsByStatus(HttpServletRequest httpServletRequest,@PathVariable RoomStatus status) {
        if(!isAdmin(httpServletRequest)) {
            throw new NotAuthorizedException("You do not have permission to access this data.");
        }

        return roomService.getRoomsByStatus(status);
    }

    @GetMapping("/sorted-by-price")
    public List<RoomDTO> getRoomsSortedByPriceAscending() {
        return roomService.getRoomsSortedByPriceAscending();
    }

    @GetMapping("/price-range")
    public List<RoomDTO> getAvailableRoomsInPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return roomService.getRoomsInPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/capacity/{capacity}")
    public List<RoomDTO> getAvailableRoomsByCapacity(@PathVariable RoomCapacity capacity) {
        return roomService.getRoomsByCapacity(capacity);
    }

    @GetMapping("/class/{roomClass}")
    public List<RoomDTO> getAvailableRoomsByClass(@PathVariable RoomClass roomClass) {
        return roomService.getRoomsByClass(roomClass);
    }

    @GetMapping("/available")
    public List<RoomDTO> getAvailableRooms(@RequestParam LocalDate startDate,
                                           @RequestParam LocalDate endDate) {
        if(startDate == null || endDate == null) {
            return roomService.getAvailableRooms(LocalDate.now(), LocalDate.now().plusDays(30));
        }
        // if end date is set before start date, return bad input exception
        if(startDate.compareTo(endDate) > 0) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        return roomService.getAvailableRooms(startDate,endDate);
    }

    private boolean isAdmin(HttpServletRequest request) {
        if(request.isUserInRole("ROLE_USER")) {
            return false;
        }
        return true;
    }

}


