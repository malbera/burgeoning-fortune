package com.swissre.wallet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WalletDTO {

    private final String currency;
    private final Map<String, BigDecimal> cryptoAmount;

    public WalletDTO(String currency,
                     Map<String, BigDecimal> cryptoAmount) {
        this.currency = currency;
        this.cryptoAmount = cryptoAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public Map<String, BigDecimal> getCryptoAmount() {
        return new HashMap<>(cryptoAmount);
    }
}
