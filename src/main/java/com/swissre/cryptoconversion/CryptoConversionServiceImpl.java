package com.swissre.cryptoconversion;

import com.swissre.cryptoprice.CryptoPriceDTO;
import com.swissre.wallet.WalletDTO;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.stream.Collectors;

public class CryptoConversionServiceImpl implements CryptoConversionService {

    @Override
    public CryptoConversionDTO covertCrypto(WalletDTO walletDto,
                                            CryptoPriceDTO cryptoPriceDto) {
        validateCurrency(walletDto, cryptoPriceDto);
        String currency = walletDto.getCurrency();
        Map<String, BigDecimal> cryptoPrice = cryptoPriceDto.getCryptoPrice();
        Map<String, BigDecimal> convertedCrypto = walletDto.getCryptoAmount().entrySet().stream()
                .map(entry -> this.convertCrypto(entry, cryptoPrice))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        return new CryptoConversionDTO(currency, convertedCrypto);
    }

    private SimpleEntry<String, BigDecimal> convertCrypto(Map.Entry<String, BigDecimal> entry,
                                                          Map<String, BigDecimal> cryptoPrices) {
        String crypto = entry.getKey();
        BigDecimal cryptoPrice = cryptoPrices.get(crypto);
        if (cryptoPrice != null) {
            BigDecimal cryptoAmount = entry.getValue();
            return new SimpleEntry<>(crypto, cryptoPrice.multiply(cryptoAmount));
        } else {
            throw new CryptoConversionException(String.format("Can't find price for crypto: %s", crypto));
        }
    }

    private void validateCurrency(WalletDTO walletDto,
                                  CryptoPriceDTO cryptoPriceDto) {
        if (!walletDto.getCurrency().equals(cryptoPriceDto.getCurrency())) {
            throw new CryptoConversionException("Currency mismatch");
        }
    }
}
