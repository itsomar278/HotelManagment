package Omar.HotelWebServer.dataAccess.repository;

import Omar.HotelWebServer.dataAccess.model.classes.Room;
import Omar.HotelWebServer.dataAccess.model.enums.RoomCapacity;
import Omar.HotelWebServer.dataAccess.model.enums.RoomClass;
import Omar.HotelWebServer.dataAccess.model.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
