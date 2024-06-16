package Omar.HotelWebServer.utils.Mapper;

import Omar.HotelWebServer.dataAccess.model.classes.Booking;
import Omar.HotelWebServer.dataAccess.model.classes.User;
import Omar.HotelWebServer.utils.DTOs.BookingDTO;
import Omar.HotelWebServer.utils.DTOs.BookingDateRangeDTO;
import Omar.HotelWebServer.utils.DTOs.BookingHistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);


    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "userId", source = "user.id")
    BookingDTO toBookingDTO(Booking booking);

    List<BookingDTO> toBookingDTOList(List<Booking> bookings);

    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "user.id", source = "userId")
    Booking toBooking(BookingDTO bookingDTO);

    // Mapping method for BookingHistoryDTO
    default BookingHistoryDTO toBookingHistoryDTO(List<Booking> bookings, User user) {
        List<BookingDTO> bookingDTOs = toBookingDTOList(bookings);
        BookingHistoryDTO historyDTO = new BookingHistoryDTO();
        historyDTO.setUsername(user.getUsername());
        historyDTO.setBookingHistory(bookingDTOs);
        return historyDTO;
    }

    // Mapping method for BookingDateRangeDTO
    default BookingDateRangeDTO toBookingDateRangeDTO(List<Booking> bookings) {
        List<BookingDTO> bookingDTOs = toBookingDTOList(bookings);
        BookingDateRangeDTO dateRangeDTO = new BookingDateRangeDTO();
        dateRangeDTO.setBookingsWithinDateRange(bookingDTOs);
        return dateRangeDTO;
    }

    // Update existing entity from DTO
    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "user.id", source = "userId")
    void updateBookingFromDTO(BookingDTO bookingDTO, @MappingTarget Booking booking);

}
