package com.codechallenge.discogs_client.exceptions;

public class DiscogsApiException extends RuntimeException {
    public DiscogsApiException(String message) {
        super(message);
    }

    public DiscogsApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
