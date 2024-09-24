package de.mayer.penandpaperdmhelper.hue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mayer.penandpaperdmhelper.hue.model.HueConfiguration;
import de.mayer.penandpaperdmhelper.hue.model.api.ApiErrorResponse;
import de.mayer.penandpaperdmhelper.hue.model.api.ApiRequest;
import de.mayer.penandpaperdmhelper.hue.model.api.ApiSuccessResponse;
import de.mayer.penandpaperdmhelper.hue.model.api.HueApiObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
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
import java.util.List;

@Service
public class HueHttpApi {


    private static final Logger log = LogManager.getLogger(HueHttpApi.class);
    private SSLContext sslContext;
    private ObjectMapper objectMapper;


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
        this.objectMapper = new ObjectMapper();
    }

    public boolean isIpOk(String ip) {
        HttpResponse<String> response = GET(ip, null, Path.GetBridge);
        if (response == null) return false;
        // we do not have a token right now, this is okay
        return response.statusCode() == HttpStatus.NOT_FOUND.value()
                && response.body().contains("hue-logo");
    }

    public boolean isConfigOk(HueConfiguration configuration) {
        var resp = GET(configuration, Path.GetBridge);
        if (resp == null) return false;
        return resp.statusCode() == HttpStatus.OK.value();
    }

    public HueConfiguration requestToken(HueConfiguration configuration) throws HueButtonNotPressedException {
        var newConfiguration = new HueConfiguration();
        newConfiguration.setIp(configuration.getIp());
        var resp = POST(configuration, Path.PostApi, new ApiRequest("pen-and-paper-dm-helper", true));
        if (resp == null) return null;

        if (resp.body().contains("\"error\"")){
            List<ApiErrorResponse> apiErrorResponses = objectMapper
                    .convertValue(resp.body(),
                            objectMapper.getTypeFactory().constructCollectionType(List.class, ApiErrorResponse.class));
            var error = apiErrorResponses.get(0).error();
            log.error("Error from Api: {}", error.description());
            if (error.type() == 101) {
                throw new HueButtonNotPressedException();
            }
        } else {
            List<ApiSuccessResponse> apiResponses = objectMapper
                    .convertValue(resp.body(),
                            objectMapper.getTypeFactory().constructCollectionType(List.class, ApiSuccessResponse.class));
            if (apiResponses != null && !apiResponses.isEmpty()) {
                newConfiguration.setToken(apiResponses.get(0).success().username());
            }
        }
        return newConfiguration;
    }

    private HttpResponse<String> POST(HueConfiguration configuration, Path path, HueApiObject apiObject) {
        HttpResponse<String> resp = null;
        try {
            HttpClient client = getClient();
            var reqBuilder = beginBuildingRequest(configuration.getIp(), configuration.getToken(), path);
            reqBuilder.POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(apiObject)));
            resp = client.send(reqBuilder.build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.warn("Failed to connect to IP {} with Path {}", configuration.getIp(), path, e);
            return null;
        }
        log.trace("Response {} - {}", resp.statusCode(), resp.body());
        return resp;
    }

    private HttpResponse<String> GET(HueConfiguration configuration, Path path) {
        return GET(configuration.getIp(), configuration.getToken(), path);
    }

    private HttpResponse<String> GET(String ip, String token, Path path) {
        HttpResponse<String> response;
        HttpClient client = null;
        try {
            client = getClient();
            var reqBuilder = beginBuildingRequest(ip, token, path);

            response = client.send(
                    reqBuilder
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.warn("Failed to connect to {}", ip, e);
            return null;
        }
        log.trace("Response: {} - {}", response.statusCode(),
                response.body().substring(0, Math.min(response.body().length(), 30)));
        return response;
    }

    private HttpClient getClient() {
        return HttpClient.newBuilder().sslContext(sslContext).build();
    }

    private static HttpRequest.Builder beginBuildingRequest(String ip, String token, Path path) {
        var reqBuilder = HttpRequest
                .newBuilder()
                .uri(getUri(ip, path));
        if (token != null) {
            reqBuilder.setHeader("hue-application-key", token);
        }
        return reqBuilder;
    }

    private static URI getUri(String ip, Path paths) {
        return URI.create("https://%s%s".formatted(ip, paths.toString()));
    }

    private enum Path {
        PostApi {
            @Override
            public String toString() {
                return "/api";
            }
        }, GetBridge {
            @Override
            public String toString() {
                return "/clip/resource/bridge";
            }
        }
    }

}
