package Omar.HotelWebServer.utils.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionResponse {
    private String message;
    private LocalDateTime dateTime;
}