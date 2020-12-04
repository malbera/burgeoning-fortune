package com.swissre.wallet;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileWalletReadTest {

    private static final String LINE_DELIMITER = "=";

    @Test
    void test_getWallet_correctValues() {
        WalletReaderService walletRead = new FileWalletReaderService(LINE_DELIMITER);
        String[] args = {"EUR", getFilePath("bobs_crypto_correct.txt")};

        WalletDTO result = walletRead.getWallet(args);

        Map<String, BigDecimal> cryptoAmount = result.getCryptoAmount();
        assertEquals("EUR", result.getCurrency());
        assertEquals(cryptoAmount.size(), 2);
        assertEquals(cryptoAmount.get("BTC"), BigDecimal.valueOf(10.11));
        assertEquals(cryptoAmount.get("ETH"), BigDecimal.valueOf(5.12));
    }

    @Test
    void test_getWallet_duplicatesCheck() {
        WalletReaderService walletRead = new FileWalletReaderService(LINE_DELIMITER);
        String[] args = {"EUR", getFilePath("bobs_crypto_duplicates.txt")};

        WalletDTO result = walletRead.getWallet(args);

        Map<String, BigDecimal> cryptoAmount = result.getCryptoAmount();
        assertEquals("EUR", result.getCurrency());
        assertEquals(cryptoAmount.size(), 2);
        assertEquals(cryptoAmount.get("BTC"), BigDecimal.valueOf(19.26));
        assertEquals(cryptoAmount.get("ETH"), BigDecimal.valueOf(12.39));
    }

    @Test
    void test_getWallet_noAmountPreset() {
        WalletReaderService walletRead = new FileWalletReaderService(LINE_DELIMITER);
        String[] args = {"EUR", getFilePath("bobs_crypto_corrupted_no_amount.txt")};

        assertThrows(WalletException.class, () -> walletRead.getWallet(args));
    }

    @Test
    void test_getWallet_multipleDelimitersPerLine() {
        WalletReaderService walletRead = new FileWalletReaderService(LINE_DELIMITER);
        String[] args = {"EUR", getFilePath("bobs_crypto_corrupted_multiple_delimiters.txt")};

        assertThrows(WalletException.class, () -> walletRead.getWallet(args));
    }


    @Test
    void test_getWallet_argumentValidation() {
        WalletReaderService walletRead = new FileWalletReaderService(LINE_DELIMITER);
        String[] currencyEmpty = {"", getFilePath("bobs_crypto_duplicates.txt")};
        String[] currencyIsNull = {null, getFilePath("bobs_crypto_duplicates.txt")};
        String[] filePathEmpty = {"EUR", ""};
        String[] filePathIsNull = {"EUR", null};
        String[] emptyArgs = {};

        assertThrows(WalletException.class, () -> walletRead.getWallet(currencyEmpty));
        assertThrows(WalletException.class, () -> walletRead.getWallet(currencyIsNull));
        assertThrows(WalletException.class, () -> walletRead.getWallet(filePathEmpty));
        assertThrows(WalletException.class, () -> walletRead.getWallet(filePathIsNull));
        assertThrows(WalletException.class, () -> walletRead.getWallet(emptyArgs));
    }

    private String getFilePath(String fileName) {
        return Paths.get("src/test/resources", fileName)
                .toFile()
                .getAbsolutePath();
    }
}