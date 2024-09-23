package de.mayer.penandpaperdmhelper.setup.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.net.ssl.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Service
public class HueHttpApi {


    private static final Logger log = LogManager.getLogger(HueHttpApi.class);
    private SSLContext sslContext;

    @PostConstruct
    private void postConstruct() throws NoSuchAlgorithmException, KeyManagementException {
        var trustManager = new X509ExtendedTrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {

            }
        };
        // HueAPI does not want us to validate SSL
        // see: https://developers.meethue.com/develop/hue-api-v2/getting-started/
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());

    }

    public boolean ok(String ip) {
        HttpResponse<String> response;
        try (var client = HttpClient.newBuilder().sslContext(sslContext).build()) {
            response = client.send(HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(URI.create(getBridgePath(ip)))
                    .build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.warn("Failed to connect to {}", ip, e);
            return false;
        }
        log.trace("Response: {}", response);
        return response.statusCode() == HttpStatus.OK.value();
    }


    private static String getBridgePath(String ip) {
        return "https://%s/resource/bridge";
    }


}
