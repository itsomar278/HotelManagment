package Omar.HotelWebServer.utils.DTOs;

import Omar.HotelWebServer.dataAccess.model.enums.PaymentMethod;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckInDTO {
    private  Integer bookingId;
    private PaymentMethod paymentMethod;
}
