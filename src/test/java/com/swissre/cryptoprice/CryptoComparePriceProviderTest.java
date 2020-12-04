package com.swissre.cryptoprice;

import com.swissre.wallet.WalletDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CryptoComparePriceProviderTest {

    private static final String URL_API = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=%s&tsyms=%s&relaxedValidation=false";

    @Test
    void test_getCryptoPrice_shouldCallApiAndGetCryptoRates() {
        CryptoComparePriceService cryptoComparePriceProvider = new CryptoComparePriceService(URL_API);
        Map<String, BigDecimal> cryptoCoins = new HashMap<>();
        cryptoCoins.put("BTC", BigDecimal.ONE);
        cryptoCoins.put("ETH", BigDecimal.ONE);

        CryptoPriceDTO result = cryptoComparePriceProvider.getCryptoPrice(new WalletDTO("EUR", cryptoCoins));

        Map<String, BigDecimal> cryptoPrice = result.getCryptoPrice();
        assertEquals("EUR", result.getCurrency());
        assertEquals(2, cryptoPrice.size());
        assertTrue(cryptoPrice.containsKey("BTC"));
        assertTrue(cryptoPrice.containsKey("ETH"));
    }

    @Test
    void test_getCryptoPrice_wrongCryptoCurrency() {
        CryptoComparePriceService cryptoComparePriceProvider = new CryptoComparePriceService(URL_API);
        Map<String, BigDecimal> cryptoCoins = new HashMap<>();
        cryptoCoins.put("ABC1", BigDecimal.ONE);

        assertThrows(CryptoPriceException.class, () -> cryptoComparePriceProvider.getCryptoPrice(new WalletDTO("EUR", cryptoCoins)));
    }

    @Test
    void test_getCryptoPrice_wrongCurrency() {
        CryptoComparePriceService cryptoComparePriceProvider = new CryptoComparePriceService(URL_API);
        Map<String, BigDecimal> cryptoCoins = new HashMap<>();
        cryptoCoins.put("BTC", BigDecimal.ONE);

        assertThrows(CryptoPriceException.class, () -> cryptoComparePriceProvider.getCryptoPrice(new WalletDTO("EUR1", cryptoCoins)));
    }
}