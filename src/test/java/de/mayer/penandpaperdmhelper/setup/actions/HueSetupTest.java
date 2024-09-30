package de.mayer.penandpaperdmhelper.setup.actions;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import de.mayer.ConfigTest;
import de.mayer.penandpaperdmhelper.CookieKeys;
import de.mayer.penandpaperdmhelper.hue.HueButtonNotPressedException;
import org.apache.struts2.junit.StrutsSpringTestCase;
import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;

import javax.servlet.http.Cookie;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HueSetupTest extends StrutsSpringTestCase {

    @Override
    protected void setupBeforeInitDispatcher() throws Exception {
        applicationContext = new AnnotationConfigApplicationContext("de.mayer.penandpaperdmhelper");
        super.setupBeforeInitDispatcher();
    }

    @Override
    protected void setUp() throws Exception {
        wireMockRule.start();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        wireMockRule.stop();
        super.tearDown();
    }

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(
            options()
                    .dynamicHttpsPort());

    @Rule
    public WireMockClassRule wireMockInstance = wireMockRule;

    ConfigTest configTest = ConfigTest.getInstance();

    public HueSetupTest() throws Exception {
    }


    public void testHueSetupActionHasHueSetupAsClass() {
        configTest.assertActionHasClass("/setup", "HueSetup", HueSetup.class.getName());
    }

    public void testRequestTokenRaisesButtonNotPressed() throws Exception {
        wireMockRule.stubFor(get(urlEqualTo("/clip/resource/bridge"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withBody("hue-logo")));

        wireMockRule.stubFor(post(urlPathEqualTo("/api"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("""
                                [{"error":{"type":101,"address":"","description":"link button not pressed"}}]""")));

        request.setCookies(new Cookie(CookieKeys.HueIp.toString(), "0.0.0.0:" + wireMockInstance.httpsPort()));

        var proxy = getActionProxy("/setup/GetHueToken.action");
        var result = proxy.execute();
        assertThat(result, is(HueSetup.ERROR));
        var exception = (Throwable) proxy
                .getInvocation()
                .getStack()
                .findValue("exception");
        assertThat(exception, is(instanceOf(HueButtonNotPressedException.class)));

    }


}