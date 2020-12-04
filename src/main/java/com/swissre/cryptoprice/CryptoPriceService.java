package com.swissre.cryptoprice;

import com.swissre.wallet.WalletDTO;

public interface CryptoPriceService {

    CryptoPriceDTO getCryptoPrice(WalletDTO walletDto);

}
