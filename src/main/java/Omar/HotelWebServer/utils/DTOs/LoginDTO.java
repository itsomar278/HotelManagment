package Omar.HotelWebServer.utils.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class LoginDTO {
    private String username;
    private String password;

}
