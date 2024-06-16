package Omar.HotelWebServer.utils.DTOs;

import Omar.HotelWebServer.dataAccess.model.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class BookingDTO {

    private Integer id;
    private Integer roomId;
    private Integer userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;
    private LocalDate createdAt;
    private Double pricePerNight;

    public BookingDTO(Integer roomId, Integer userId, LocalDate startDate, LocalDate endDate) {
        this.roomId = roomId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    // ALL ARGS CONSTRUCTOR
    public BookingDTO(Integer id, Integer roomId, Integer userId, LocalDate startDate, LocalDate endDate, BookingStatus status, LocalDate createdAt, Double pricePerNight) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.pricePerNight = pricePerNight;
    }
    // NO ARGS CONSTRUCTOR
    public BookingDTO() {
    }
    public BookingDTO(Integer id, Integer roomId, Integer userId, LocalDate startDate, LocalDate endDate, BookingStatus status, LocalDate createdAt) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
    }

}

