package Omar.HotelWebServer.utils.DTOs;

import lombok.Data;

@Data
public class AuthResponseDTO {
    public String token;
    public String tokenType = "Bearer";

    public AuthResponseDTO(String token) {
        this.token = token;
    }


}
