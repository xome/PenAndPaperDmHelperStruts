package de.mayer.penandpaperdmhelper.hue;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.mayer.penandpaperdmhelper.hue.model.HueConfiguration;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(httpsEnabled = true)
class HueHttpApiTest {

    static HueHttpApi hueHttpApi = new HueHttpApi();

    @BeforeAll
    static void beforeAll() throws NoSuchAlgorithmException, KeyManagementException {
        hueHttpApi.postConstruct();
    }

    @Test
    @DisplayName("""
            Given Hue /api responses with an Error
            When the ObjectMapper is used to cast the JSON to a ApiErrorResponse
            Then an object is created""")
    void requestTokenCanParseErrorResponse(WireMockRuntimeInfo wmRuntimeInfo) {
        stubFor(post(urlPathEqualTo("/api"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("""
                                [{"error":{"type":101,"address":"","description":"link button not pressed"}}]""")));

        assertThrows(HueButtonNotPressedException.class, () -> {
            hueHttpApi.requestToken(new HueConfiguration("0.0.0.0:" + wmRuntimeInfo.getHttpsPort(), null));
        });
    }

    @Test
    @DisplayName("""
            Given Hue /api responses with success,
            When the response is parsed,
            Then the username field is returned as token in HueConfiguration""")
    void requestTokenSuccess(WireMockRuntimeInfo wmRuntimeInfo) throws HueButtonNotPressedException {

        var tokenExpected = "token1234";

        stubFor(post(urlPathEqualTo("/api"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("""
                                [{"success":{"username":"%s", "clientkey": "clientKey1234"}}]"""
                                .formatted(tokenExpected))));

        var configuration = hueHttpApi.requestToken(new HueConfiguration("0.0.0.0:" + wmRuntimeInfo.getHttpsPort(), null));
        assertThat(configuration.getToken(), is(tokenExpected));

    }

}