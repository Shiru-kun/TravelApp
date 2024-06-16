package co.mz.vodafone.TravelApp.exceptions;

public class InternalServerErrorException extends RuntimeException{
    private String message;

    public InternalServerErrorException() {}

    public InternalServerErrorException(String msg) {
        super(msg);
        this.message = msg;
    }
}
