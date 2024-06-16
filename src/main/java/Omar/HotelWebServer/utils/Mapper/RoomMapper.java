package Omar.HotelWebServer.utils.Mapper;

import Omar.HotelWebServer.dataAccess.model.classes.Room;
import Omar.HotelWebServer.utils.DTOs.RoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(target = "id", ignore = true)
    Room toRoomEntity(RoomDTO dto);

    RoomDTO toRoomDTO(Room room);

    void updateRoomFromDTO(RoomDTO dto, @MappingTarget Room room);
}
