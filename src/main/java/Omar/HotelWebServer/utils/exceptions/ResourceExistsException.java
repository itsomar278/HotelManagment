package Omar.HotelWebServer.utils.exceptions;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException() {
        super("Resource Already exists");
    }

    public ResourceExistsException(String message) {
        super(message);
    }

}