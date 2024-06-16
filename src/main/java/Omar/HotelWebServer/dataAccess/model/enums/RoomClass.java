package Omar.HotelWebServer.dataAccess.model.enums;


public enum RoomClass {
    ECONOMY(150),
    BUSINESS(250),
    LUXURY(450),
    DIPLOMATIC(1000);

    private final int value;

    RoomClass(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
