package com.swissre;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

class BurgeoningFortuneTest {

    @Test
    void showBobsCryptoPortfolio_smokeTest() {
        String filePath = getFilePath();
        String [] args = {"EUR", filePath};
        BurgeoningFortune burgeoningFortune = new BurgeoningFortune();
        burgeoningFortune.showBobsCryptoPortfolio(args);
    }

    private String getFilePath() {
        return Paths.get("src/test/resources", "bobs_crypto_correct.txt")
                .toFile()
                .getAbsolutePath();
    }
}