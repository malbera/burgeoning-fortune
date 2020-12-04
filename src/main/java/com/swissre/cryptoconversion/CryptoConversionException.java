package com.swissre.cryptoconversion;

public class CryptoConversionException extends RuntimeException {

    public CryptoConversionException(String message) {
        super(message);
    }

    public CryptoConversionException(Throwable cause) {
        super(cause);
    }
}
