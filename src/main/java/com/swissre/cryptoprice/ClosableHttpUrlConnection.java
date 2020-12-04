package com.swissre.cryptoprice;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ClosableHttpUrlConnection implements AutoCloseable {

    private final URLConnection connection;

    public ClosableHttpUrlConnection(String urlApi) {
        try {
            this.connection = new URL(urlApi).openConnection();
            this.connection.connect();
        } catch (IOException e) {
            throw new CryptoPriceException(e);
        }
    }

    InputStream getInputStream() {
        try {
            return this.connection.getInputStream();
        } catch (IOException e) {
            throw new CryptoPriceException(e);
        }
    }

    @Override
    public void close() throws Exception {
        this.getInputStream().close();
    }
}
