package co.mz.vodafone.TravelApp.exceptions;

public class BadRequestException extends RuntimeException{
    private String message;

    public BadRequestException() {}

    public BadRequestException(String msg) {
        super(msg);
        this.message = msg;
    }
}
