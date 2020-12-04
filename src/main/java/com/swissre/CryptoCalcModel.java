package com.swissre;

import java.math.BigDecimal;
import java.util.Map;

public class CryptoCalcModel {

    private final String currency;
    private final Map<String, BigDecimal> cryptoCoinValue;
    private final BigDecimal totalAmount;

    public CryptoCalcModel(String currency,
                           Map<String, BigDecimal> cryptoCoinValue,
                           BigDecimal totalAmount) {
        this.currency = currency;
        this.cryptoCoinValue = cryptoCoinValue;
        this.totalAmount = totalAmount;
    }
}
