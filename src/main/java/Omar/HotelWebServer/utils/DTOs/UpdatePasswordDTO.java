package Omar.HotelWebServer.utils.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePasswordDTO {
    String oldPassword;
    String newPassword;

}
