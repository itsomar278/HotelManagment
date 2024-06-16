package Omar.HotelWebServer.dataAccess.repository;

import Omar.HotelWebServer.dataAccess.model.classes.Booking;
import Omar.HotelWebServer.dataAccess.model.classes.Room;
import Omar.HotelWebServer.dataAccess.model.classes.User; // Assuming User entity exists
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByUserOrderByStartDateDesc(User user);

    List<Booking> findAllByEndDateAfter(LocalDate date);

    List<Booking> findAllByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Booking> findAllByRoom(Room room);

    List<Booking> findByRoomId(Integer roomId);
    List<Booking> findByUserId(Integer userId);


    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
            "AND ((b.startDate BETWEEN :startDate AND :endDate) OR (b.endDate BETWEEN :startDate AND :endDate))")
    List<Booking> findOverlappingBookings(@Param("roomId") Integer roomId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
}
