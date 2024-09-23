package de.mayer.penandpaperdmhelper.setup.actions;

import com.opensymphony.xwork2.ActionSupport;
import de.mayer.penandpaperdmhelper.CookieKeys;
import de.mayer.penandpaperdmhelper.hue.model.HueConfiguration;
import de.mayer.penandpaperdmhelper.hue.HueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.ServletResponseAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class HueSetup extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private static final Logger log = LogManager.getLogger(HueSetup.class);
    private final HueService hueService;
    private String hueIp;
    private String hueToken;
    private HttpServletResponse response;

    private HueConfiguration hueConfigurationBean;
    private HttpServletRequest request;

    public HueSetup(HueService hueService) {
        this.hueService = hueService;
    }

    @Override
    public String execute() throws Exception {
        var cookieHueIp = getOptionalCookieValue(CookieKeys.HueIp.toString());
        var cookieHueToken = getOptionalCookieValue(CookieKeys.HueToken.toString());

        var hueConfig = hueService.checkHueOk(cookieHueIp, cookieHueToken);
        setHueIp(hueConfig.getIp());
        setHueToken(hueConfig.getToken());
        return SUCCESS;
    }

    private Optional<String> getOptionalCookieValue(String HueToken) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(HueToken))
                .map(Cookie::getValue)
                .findFirst();
    }


    public String executeForm() throws Exception {
        Cookie cookieIp = new Cookie(CookieKeys.HueIp.toString(), hueConfigurationBean.getIp());
        cookieIp.setMaxAge(-1);
        response.addCookie(cookieIp);

        Cookie cookieToken = new Cookie(CookieKeys.HueToken.toString(), hueConfigurationBean.getToken());
        cookieToken.setMaxAge(-1);
        response.addCookie(cookieToken);

        return SUCCESS;
    }

    public String requestHueToken() {
        hueService.requestToken(
                hueService.checkHueOk(
                        getOptionalCookieValue(CookieKeys.HueIp.toString()),
                        getOptionalCookieValue(CookieKeys.HueToken.toString())
                )
        );

        return SUCCESS;
    }

    @StrutsParameter(depth = 1)
    public HueConfiguration getHueConfigurationBean() {
        return hueConfigurationBean;
    }

    public void setHueConfigurationBean(HueConfiguration hueConfigurationBean) {
        this.hueConfigurationBean = hueConfigurationBean;
    }

    public String getHueIp() {
        return hueIp;
    }

    public void setHueIp(String hueIp) {
        this.hueIp = hueIp;
    }

    public String getHueToken() {
        return hueToken;
    }

    public void setHueToken(String hueToken) {
        this.hueToken = hueToken;
    }

    @Override
    public void withServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
