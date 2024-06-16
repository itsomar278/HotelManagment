package Omar.HotelWebServer.utils.exceptions;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException() {
        super("Not Authorized");
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
