package com.swissre.cryptoprice;

import com.swissre.wallet.WalletDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CryptoComparePriceService implements CryptoPriceService {

    private static final int AMOUNT_INDEX = 1;
    private static final Pattern RESPONSE_ERROR = Pattern.compile("\"Response\":\"Error\"");
    private static final Pattern ERROR_MESSAGE = Pattern.compile("\"Message\":\"(.*?)\"");
    private static final String RESPONSE_PRICE_FORMAT = "\"%s\":\\{\"%s\":(.*?)\\}";
    private static final String CURRENCY_GENERIC_ERROR = "Error getting crypto prices, please check currency/coin naming";

    private final String apiUrl;

    public CryptoComparePriceService(String urlApi) {
        this.apiUrl = urlApi;
    }

    private static boolean hasErrors(String response) {
        return RESPONSE_ERROR.matcher(response).find();
    }

    private static String getErrorMessage(String response) {
        Matcher matcher = ERROR_MESSAGE.matcher(response);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return CURRENCY_GENERIC_ERROR;
        }
    }

    private static BiConsumer<HashMap<String, BigDecimal>, SimpleEntry<String, String>> lineAccumulator() {
        return (map, entry) -> {
            String currency = entry.getKey();
            String amount = entry.getValue();
            map.put(currency, new BigDecimal(amount));
        };
    }

    @Override
    public CryptoPriceDTO getCryptoPrice(WalletDTO walletDto) {
        String currency = walletDto.getCurrency();
        Set<String> cryptoCoins = walletDto.getCryptoAmount().keySet();
        String cryptoCoinsArgument = getCryptoCoins(cryptoCoins);
        String response = executeCall(cryptoCoinsArgument, currency);
        if (hasErrors(response)) {
            String errorMessage = getErrorMessage(response);
            throw new CryptoPriceException(errorMessage);
        } else {
            Map<String, BigDecimal> cryptoPrice = cryptoCoins.stream()
                    .map(coin -> this.getCoinPriceMapping(coin, response, currency))
                    .collect(HashMap::new, lineAccumulator(), Map::putAll);
            return new CryptoPriceDTO(currency, cryptoPrice);
        }
    }

    private SimpleEntry<String, String> getCoinPriceMapping(String cryptoCoin,
                                                            String response,
                                                            String currency) {
        Pattern pattern = Pattern.compile(String.format(RESPONSE_PRICE_FORMAT, cryptoCoin, currency));
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            String coinPrice = matcher.group(AMOUNT_INDEX);
            return new SimpleEntry<>(cryptoCoin, coinPrice);
        } else {
            throw new CryptoPriceException(String.format("Can't find price for %s", cryptoCoin));
        }
    }

    private String getCryptoCoins(Set<String> cryptoCoins) {
        return String.join(",", cryptoCoins);
    }

    private String executeCall(String cryptoCoins,
                               String currency) {
        try (ClosableHttpUrlConnection conn = new ClosableHttpUrlConnection(String.format(apiUrl, cryptoCoins, currency));
             BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (Exception e) {
            throw new CryptoPriceException(e);
        }
    }

}
