package com.swissre.cryptoportfolio;

import com.swissre.cryptoconversion.CryptoConversionDTO;

import java.math.BigDecimal;
import java.util.Map;

public class CryptoPortfolioServiceImpl implements CryptoPortfolioService {

    @Override
    public void displayPortfolio(CryptoConversionDTO cryptoConversionDto) {
        String currency = cryptoConversionDto.getCurrency();
        Map<String, BigDecimal> convertedCrypto = cryptoConversionDto.getConvertedCrypto();
        BigDecimal cryptoTotalPrice = cryptoTotalPrice(convertedCrypto);
        System.out.printf("Bob's crypto portfolio for currency: %s%n", currency);
        System.out.printf("Total crypto balance: %s (%s)%n", cryptoTotalPrice, currency);
        convertedCrypto.entrySet().forEach(e -> this.displayCryptoAmount(e, currency));
    }

    private BigDecimal cryptoTotalPrice(Map<String, BigDecimal> convertedCrypto) {
        return convertedCrypto.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void displayCryptoAmount(Map.Entry<String, BigDecimal> entry,
                                     String currency) {
        String crypto = entry.getKey();
        BigDecimal cryptoValue = entry.getValue();
        System.out.printf("%s=%s (%s)%n", crypto, cryptoValue, currency);
    }
}
