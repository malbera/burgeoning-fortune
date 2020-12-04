package com.swissre.cryptoprice;

public class CryptoPriceException extends RuntimeException {

    public CryptoPriceException(String message) {
        super(message);
    }

    public CryptoPriceException(Throwable cause) {
        super(cause);
    }
}
