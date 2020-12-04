package com.swissre.cryptoconversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CryptoConversionDTO {

    private final String currency;
    private final Map<String, BigDecimal> convertedCrypto;

    public CryptoConversionDTO(String currency,
                               Map<String, BigDecimal> convertedCrypto) {
        this.currency = currency;
        this.convertedCrypto = convertedCrypto;
    }

    public String getCurrency() {
        return currency;
    }

    public Map<String, BigDecimal> getConvertedCrypto() {
        return new HashMap<>(convertedCrypto);
    }
}
