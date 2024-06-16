package co.mz.vodafone.TravelApp.exceptions;

public class ForbiddenException extends RuntimeException{
    private String message;

    public ForbiddenException() {}

    public ForbiddenException(String msg) {
        super(msg);
        this.message = msg;
    }
}
