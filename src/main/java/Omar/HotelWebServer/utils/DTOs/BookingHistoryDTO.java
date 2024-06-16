package Omar.HotelWebServer.utils.DTOs;

import java.util.List;

public class BookingHistoryDTO {
    private String username;
    private List<BookingDTO> bookingHistory;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BookingDTO> getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(List<BookingDTO> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }
}
