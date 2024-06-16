package co.mz.vodafone.TravelApp.exceptions;

public class NoSuchElementException extends RuntimeException{
    private String message;

    public NoSuchElementException() {}

    public NoSuchElementException(String msg) {
        super(msg);
        this.message = msg;
    }
}
