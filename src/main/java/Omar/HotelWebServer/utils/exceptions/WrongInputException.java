package Omar.HotelWebServer.utils.exceptions;

public class WrongInputException extends RuntimeException{

    public WrongInputException(String message )
    {
        super(message);
    }
    public WrongInputException()
    {
        super("Wrong Input ");
    }


}