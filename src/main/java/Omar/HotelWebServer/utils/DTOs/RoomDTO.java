package Omar.HotelWebServer.utils.DTOs;

import Omar.HotelWebServer.dataAccess.model.enums.RoomCapacity;
import Omar.HotelWebServer.dataAccess.model.enums.RoomClass;
import Omar.HotelWebServer.dataAccess.model.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class RoomDTO {

    private Integer id;

    @NotNull
    private Integer floor;

    @NotNull
    private RoomCapacity roomCapacity;

    @NotNull
    private RoomClass roomClass;

    @NotNull
    private RoomStatus status;

    @NotNull
    private Double price;

}
