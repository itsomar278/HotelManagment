package Omar.HotelWebServer.utils.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDateRangeDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private List<BookingDTO> bookingsWithinDateRange;
}
