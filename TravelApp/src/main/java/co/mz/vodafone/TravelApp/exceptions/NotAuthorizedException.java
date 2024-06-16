package co.mz.vodafone.TravelApp.exceptions;

public class NotAuthorizedException extends RuntimeException{
    private String message;

    public NotAuthorizedException() {}

    public NotAuthorizedException(String msg) {
        super(msg);
        this.message = msg;
    }
}
