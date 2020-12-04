package com.swissre.cryptoconversion;

import com.swissre.cryptoprice.CryptoPriceDTO;
import com.swissre.wallet.WalletDTO;

public interface CryptoConversionService {

    CryptoConversionDTO covertCrypto(WalletDTO walletDto, CryptoPriceDTO cryptoPriceDto);

}
