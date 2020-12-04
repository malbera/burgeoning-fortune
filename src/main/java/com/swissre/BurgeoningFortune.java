package com.swissre;

import com.swissre.cryptoconversion.CryptoConversionDTO;
import com.swissre.cryptoconversion.CryptoConversionService;
import com.swissre.cryptoconversion.CryptoConversionServiceImpl;
import com.swissre.cryptoportfolio.CryptoPortfolioService;
import com.swissre.cryptoportfolio.CryptoPortfolioServiceImpl;
import com.swissre.cryptoprice.CryptoComparePriceService;
import com.swissre.cryptoprice.CryptoPriceDTO;
import com.swissre.cryptoprice.CryptoPriceService;
import com.swissre.wallet.FileWalletReaderService;
import com.swissre.wallet.WalletDTO;
import com.swissre.wallet.WalletReaderService;

public class BurgeoningFortune {

    private static final String URL_API = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=%s&tsyms=%s&relaxedValidation=false";
    private static final String LINE_DELIMITER = "=";

    private final CryptoPriceService cryptoPriceService;
    private final WalletReaderService walletReaderService;
    private final CryptoConversionService cryptoConversionService;
    private final CryptoPortfolioService cryptoPortfolioService;

    BurgeoningFortune() {
        this.cryptoPriceService = new CryptoComparePriceService(URL_API);
        this.walletReaderService = new FileWalletReaderService(LINE_DELIMITER);
        this.cryptoConversionService = new CryptoConversionServiceImpl();
        this.cryptoPortfolioService = new CryptoPortfolioServiceImpl();
    }

    public static void main(String[] args) {
        BurgeoningFortune burgeoningFortune = new BurgeoningFortune();
        burgeoningFortune.showBobsCryptoPortfolio(args);
    }

    void showBobsCryptoPortfolio(String[] args) {
        WalletDTO walletDto = walletReaderService.getWallet(args);
        CryptoPriceDTO cryptoPriceDto = cryptoPriceService.getCryptoPrice(walletDto);
        CryptoConversionDTO cryptoConversionDto = cryptoConversionService.covertCrypto(walletDto, cryptoPriceDto);
        cryptoPortfolioService.displayPortfolio(cryptoConversionDto);
    }

}
