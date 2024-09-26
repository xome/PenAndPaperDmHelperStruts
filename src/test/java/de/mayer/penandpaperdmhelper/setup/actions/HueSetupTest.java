package de.mayer.penandpaperdmhelper.setup.actions;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import de.mayer.ConfigTest;
import de.mayer.penandpaperdmhelper.CookieKeys;
import org.apache.struts2.junit.StrutsSpringTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.springframework.http.HttpStatus;

import javax.servlet.http.Cookie;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class HueSetupTest extends StrutsSpringTestCase {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(
            options()
                    .dynamicHttpsPort());

    @Rule
    public WireMockClassRule wireMockInstance = wireMockRule;

    ConfigTest configTest = ConfigTest.getInstance();

    public HueSetupTest() throws Exception {
    }

    @BeforeClass
    public static void beforeALl() throws Exception {
        wireMockRule.start();
    }

    @AfterClass
    public static void afterAll() throws Exception {
        wireMockRule.stop();
    }

    public void testHueSetupActionHasHueSetupAsClass() {
        configTest.assertActionHasClass("/setup", "HueSetup", HueSetup.class.getName());
    }

    public void testRequestTokenRaisesButtonNotPressed() throws Exception {
        wireMockInstance.start();
        wireMockInstance.stubFor(post(urlPathEqualTo("/api"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("""
                                [{"error":{"type":101,"address":"","description":"link button not pressed"}}]""")));

        request.setCookies(new Cookie(CookieKeys.HueIp.toString(), "0.0.0.0:" + wireMockInstance.httpsPort()));

        var proxy = getActionProxy("/setup/GetHueToken.action");
        proxy.execute();

    }


}