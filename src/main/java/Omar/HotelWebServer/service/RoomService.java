package Omar.HotelWebServer.service;

import Omar.HotelWebServer.dataAccess.model.classes.Room;
import Omar.HotelWebServer.dataAccess.model.enums.RoomCapacity;
import Omar.HotelWebServer.dataAccess.model.enums.RoomClass;
import Omar.HotelWebServer.dataAccess.model.enums.RoomStatus;
import Omar.HotelWebServer.dataAccess.repository.BookingRepository;
import Omar.HotelWebServer.dataAccess.repository.RoomRepository;
import Omar.HotelWebServer.utils.DTOs.RoomDTO;
import Omar.HotelWebServer.utils.Mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final RoomMapper roomMapper = RoomMapper.INSTANCE;

    @Autowired
    public RoomService(RoomRepository roomRepository,BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(roomMapper::toRoomDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO getRoomById(Integer id) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) {
            return null;
        }
        return roomMapper.toRoomDTO(room);
    }

    public RoomDTO addRoom(RoomDTO roomDTO) {
        Room room = roomMapper.toRoomEntity(roomDTO);
        room = roomRepository.save(room);
        return roomMapper.toRoomDTO(room);
    }

    public RoomDTO updateRoom(RoomDTO updatedRoomDTO) {
        Room existingRoom = roomRepository.findById(updatedRoomDTO.getId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + updatedRoomDTO.getId()));

        roomMapper.updateRoomFromDTO(updatedRoomDTO, existingRoom);

        existingRoom = roomRepository.save(existingRoom);
        return roomMapper.toRoomDTO(existingRoom);
    }

    public void deleteRoom(Integer id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }
    public List<RoomDTO> getRoomsByStatus(RoomStatus status) {
        List<Room> rooms = roomRepository.findByStatus(status);
        return rooms.stream()
                .map(RoomMapper.INSTANCE::toRoomDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getRoomsSortedByPriceAscending() {
        List<Room> rooms = roomRepository.findByOrderByPriceAsc();
        return rooms.stream()
                .map(RoomMapper.INSTANCE::toRoomDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getRoomsInPriceRange(Double minPrice, Double maxPrice) {
        List<Room> rooms = roomRepository.findByPriceBetween(minPrice, maxPrice);
        return rooms.stream()
                .map(RoomMapper.INSTANCE::toRoomDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getRoomsByCapacity(RoomCapacity roomCapacity) {
        List<Room> rooms = roomRepository.findByRoomCapacity(roomCapacity);
        return rooms.stream()
                .map(RoomMapper.INSTANCE::toRoomDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getRoomsByClass(RoomClass roomClass) {
        List<Room> rooms = roomRepository.findByRoomClass(roomClass);
        return rooms.stream()
                .map(RoomMapper.INSTANCE::toRoomDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        // get rooms which are not booked between startDate and endDate
        List<Room> rooms = roomRepository.findAvailableRooms(startDate, endDate);
        return rooms.stream()
                .map(RoomMapper.INSTANCE::toRoomDTO)
                .collect(Collectors.toList());


    }
}
