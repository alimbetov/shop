package kz.shop.book.exception;

public class LimitExceededException extends RuntimeException {
    public LimitExceededException(String message) {
        super(message);
    }
}