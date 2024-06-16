package Omar.HotelWebServer.dataAccess.model.classes;

import Omar.HotelWebServer.dataAccess.model.enums.RoomCapacity;
import Omar.HotelWebServer.dataAccess.model.enums.RoomClass;
import Omar.HotelWebServer.dataAccess.model.enums.RoomStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "floor")
    private Integer floor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "room_capacity")
    private RoomCapacity roomCapacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "room_class")
    private RoomClass roomClass;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RoomStatus status;

    @NotNull
    @Column(name = "price")
    private Double price;


}
