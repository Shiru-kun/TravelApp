package co.mz.vodafone.TravelApp.exceptions;

public class ConflictException extends RuntimeException{
    private String message;

    public ConflictException() {}

    public ConflictException(String msg) {
        super(msg);
        this.message = msg;
    }
}
