package Omar.HotelWebServer.dataAccess.model.enums;

public enum RoomCapacity {
    SINGLE_ROOM(150),
    DOUBLE_ROOM(250),
    SUITE(350),
    COMPLETE_FLOOR(450);

    private final int value;

    RoomCapacity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
