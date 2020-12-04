package com.swissre;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RestCallModel {

    private final Map<String, BigDecimal> cryptoCoinRate;

    public RestCallModel(Map<String, BigDecimal> cryptoCoinRate) {
        this.cryptoCoinRate = cryptoCoinRate;
    }

    public Map<String, BigDecimal> getCryptoCoinRate() {
        return new HashMap<>(cryptoCoinRate);
    }
}
