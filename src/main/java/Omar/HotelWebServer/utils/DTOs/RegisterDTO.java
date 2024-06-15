package Omar.HotelWebServer.utils.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterDTO {
    private String username;
    private String password;

}
