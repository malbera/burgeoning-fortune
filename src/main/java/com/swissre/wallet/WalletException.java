package com.swissre.wallet;

public class WalletException extends RuntimeException {

    public WalletException(String message) {
        super(message);
    }

    public WalletException(Throwable cause) {
        super(cause);
    }
}
