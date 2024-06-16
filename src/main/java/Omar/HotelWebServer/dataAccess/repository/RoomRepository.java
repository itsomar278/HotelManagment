package Omar.HotelWebServer.dataAccess.repository;

import Omar.HotelWebServer.dataAccess.model.classes.Room;
import Omar.HotelWebServer.dataAccess.model.enums.RoomCapacity;
import Omar.HotelWebServer.dataAccess.model.enums.RoomClass;
import Omar.HotelWebServer.dataAccess.model.enums.RoomStatus;
import Omar.HotelWebServer.utils.DTOs.RoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByStatus(RoomStatus status);

    List<Room> findByOrderByPriceAsc();

    List<Room> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Room> findByRoomCapacity(RoomCapacity roomCapacity);

    List<Room> findByRoomClass(RoomClass roomClass);

    @Query("SELECT r.price FROM Room r WHERE r.id = :roomId")
    Optional<Double> findPriceById(@Param("roomId") Integer roomId);

    @Query("SELECT r FROM Room r WHERE r.status = 'AVAILABLE' AND r.id NOT IN (SELECT b.room.id FROM Booking b WHERE b.startDate <= :endDate AND b.endDate >= :startDate)")
    List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate);

    List<Room> getRoomsByStatus(RoomStatus roomStatus);
}
