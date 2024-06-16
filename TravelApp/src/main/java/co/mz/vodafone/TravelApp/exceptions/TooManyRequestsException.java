package co.mz.vodafone.TravelApp.exceptions;

public class TooManyRequestsException extends RuntimeException{
    private String message;

    public TooManyRequestsException() {}

    public TooManyRequestsException(String msg) {
        super(msg);
        this.message = msg;
    }
}