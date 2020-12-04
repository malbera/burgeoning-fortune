package com.swissre.cryptoprice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CryptoPriceDTO {

    private final String currency;
    private final Map<String, BigDecimal> cryptoPrice;

    public CryptoPriceDTO(String currency,
                          Map<String, BigDecimal> cryptoPrice) {
        this.currency = currency;
        this.cryptoPrice = cryptoPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public Map<String, BigDecimal> getCryptoPrice() {
        return new HashMap<>(cryptoPrice);
    }
}
