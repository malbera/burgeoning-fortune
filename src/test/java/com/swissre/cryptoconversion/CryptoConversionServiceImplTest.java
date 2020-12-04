package com.swissre.cryptoconversion;

import com.swissre.cryptoprice.CryptoPriceDTO;
import com.swissre.wallet.WalletDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CryptoConversionServiceImplTest {

    @Test
    void test_covertCrypto_correctConversion() {
        CryptoConversionService conversionService = new CryptoConversionServiceImpl();
        Map<String, BigDecimal> cryptoAmount = new HashMap<>();
        Map<String, BigDecimal> cryptoPrice = new HashMap<>();
        cryptoAmount.put("EUR", BigDecimal.valueOf(15.11));
        cryptoPrice.put("EUR", BigDecimal.valueOf(123.1234));
        WalletDTO walletDto = new WalletDTO("EUR", cryptoAmount);
        CryptoPriceDTO cryptoPriceDto = new CryptoPriceDTO("EUR", cryptoPrice);

        CryptoConversionDTO result = conversionService.covertCrypto(walletDto, cryptoPriceDto);

        Map<String, BigDecimal> convertedCrypto = result.getConvertedCrypto();
        assertEquals("EUR", result.getCurrency());
        assertEquals(1, convertedCrypto.size());
        assertEquals(BigDecimal.valueOf(1860.394574), convertedCrypto.get("EUR"));
    }

    @Test
    void test_covertCrypto_wrongPriceSetup() {
        CryptoConversionService conversionService = new CryptoConversionServiceImpl();
        Map<String, BigDecimal> cryptoAmount = new HashMap<>();
        Map<String, BigDecimal> cryptoPrice = new HashMap<>();
        cryptoAmount.put("EUR", BigDecimal.valueOf(15.11));
        WalletDTO walletDto = new WalletDTO("EUR", cryptoAmount);
        CryptoPriceDTO cryptoPriceDto = new CryptoPriceDTO("EUR", cryptoPrice);

        assertThrows(CryptoConversionException.class, () -> conversionService.covertCrypto(walletDto, cryptoPriceDto));
    }

    @Test
    void test_covertCrypto_wrongCurrency() {
        CryptoConversionService conversionService = new CryptoConversionServiceImpl();
        WalletDTO walletDto = new WalletDTO("EUR", new HashMap<>());
        CryptoPriceDTO cryptoPriceDto = new CryptoPriceDTO("USD", new HashMap<>());

        assertThrows(CryptoConversionException.class, () -> conversionService.covertCrypto(walletDto, cryptoPriceDto));
    }
}