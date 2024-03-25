package com.youcode.korea2tv.exception.custom;

public class MovieAlreadyInWatchlistException extends RuntimeException {

    public MovieAlreadyInWatchlistException() {
        super();
    }

    public MovieAlreadyInWatchlistException(String message) {
        super(message);
    }

    public MovieAlreadyInWatchlistException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieAlreadyInWatchlistException(Throwable cause) {
        super(cause);
    }
}
