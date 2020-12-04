package com.swissre.wallet;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class FileWalletReaderService implements WalletReaderService {

    private static final int CURRENCY_INDEX = 0;
    private static final int FILE_PATH_INDEX = 1;
    private static final int MIN_ARGS_COUNT = 2;

    public final String lineDelimiter;

    public FileWalletReaderService(String lineDelimiter) {
        this.lineDelimiter = lineDelimiter;
    }

    private static BiConsumer<HashMap<String, BigDecimal>, String[]> lineAccumulator() {
        return (map, line) -> {
            String currency = line[CURRENCY_INDEX];
            String amount = line[FILE_PATH_INDEX];
            if (line.length != MIN_ARGS_COUNT || isEmptyString(currency) || isEmptyString(amount)) {
                throw new WalletException("Error reading line");
            }
            currency = currency.toUpperCase();
            map.computeIfPresent(currency, (k, v) -> v.add(new BigDecimal(amount)));
            map.computeIfAbsent(currency, (k) -> new BigDecimal(amount));
        };
    }

    private static boolean isEmptyString(String s) {
        return s == null || s.isEmpty();
    }

    @Override
    public WalletDTO getWallet(String[] args) {
        validateArgs(args);
        String currency = args[CURRENCY_INDEX].toUpperCase();
        String filePath = args[FILE_PATH_INDEX];
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            Map<String, BigDecimal> collect = lines.map(String::toUpperCase)
                    .map(line -> line.split(lineDelimiter))
                    .collect(HashMap::new, lineAccumulator(), Map::putAll);
            return new WalletDTO(currency, collect);
        } catch (Exception e) {
            throw new WalletException(e);
        }
    }

    private void validateArgs(String[] args) {
        if (args.length < MIN_ARGS_COUNT || isEmptyString(args[CURRENCY_INDEX]) || isEmptyString(args[FILE_PATH_INDEX])) {
            throw new WalletException("Error reading file, wrong arguments");
        }
    }

}
