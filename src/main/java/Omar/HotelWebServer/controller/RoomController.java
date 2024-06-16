package Omar.HotelWebServer.controller;

import Omar.HotelWebServer.dataAccess.model.enums.RoomCapacity;
import Omar.HotelWebServer.dataAccess.model.enums.RoomClass;
import Omar.HotelWebServer.dataAccess.model.enums.RoomStatus;
import Omar.HotelWebServer.service.RoomService;
import Omar.HotelWebServer.utils.DTOs.RoomDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<RoomDTO> addRoom(@Valid @RequestBody RoomDTO roomDTO) {
        RoomDTO addedRoom = roomService.addRoom(roomDTO);
        return ResponseEntity.ok(addedRoom);
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomDTO> updateRoom(
            @PathVariable Integer roomId,
            @Valid @RequestBody RoomDTO updatedRoomDTO) {
        updatedRoomDTO.setId(roomId);
        RoomDTO roomDTO = roomService.updateRoom(updatedRoomDTO);
        return ResponseEntity.ok(roomDTO);
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Integer roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room deleted successfully");
    }

    @GetMapping("/status/{status}")
    public List<RoomDTO> getRoomsByStatus(@PathVariable RoomStatus status) {
        return roomService.getRoomsByStatus(status);
    }

    @GetMapping("/sorted-by-price")
    public List<RoomDTO> getRoomsSortedByPriceAscending() {
        return roomService.getRoomsSortedByPriceAscending();
    }

    @GetMapping("/price-range")
    public List<RoomDTO> getRoomsInPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return roomService.getRoomsInPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/capacity/{capacity}")
    public List<RoomDTO> getRoomsByCapacity(@PathVariable RoomCapacity capacity) {
        return roomService.getRoomsByCapacity(capacity);
    }

    @GetMapping("/class/{roomClass}")
    public List<RoomDTO> getRoomsByClass(@PathVariable RoomClass roomClass) {
        return roomService.getRoomsByClass(roomClass);
    }
}


