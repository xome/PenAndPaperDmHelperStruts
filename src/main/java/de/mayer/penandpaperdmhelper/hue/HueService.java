package de.mayer.penandpaperdmhelper.hue;

import de.mayer.penandpaperdmhelper.hue.model.HueConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HueService {
    private static final Logger log = LogManager.getLogger(HueService.class);
    private final HueServiceResolver hueServiceResolver;
    private final HueHttpApi hueHttpApi;

    public HueService(HueServiceResolver hueServiceResolver, HueHttpApi hueHttpApi) {
        this.hueServiceResolver = hueServiceResolver;
        this.hueHttpApi = hueHttpApi;
    }

    public HueConfiguration checkHueOk(Optional<String> cookieHueIp, Optional<String> token) {
        HueConfiguration configuration = new HueConfiguration();

        var hueIp = cookieHueIp.map((cookie) -> {
            log.debug("Hue IP from cookie: {}", cookie);
            return cookie;
        }).orElseGet(this::getIpFromMDnsService);

        if (hueIp == null || hueIp.isEmpty()) {
            hueIp = getIpFromMDnsService();
        }

        if (hueHttpApi.isIpOk(hueIp)){
            configuration.setIp(hueIp);
        }

        if (token.isPresent() && !token.get().isEmpty()) {
            configuration.setToken(token.get());
            if (!hueHttpApi.isConfigOk(configuration))
                configuration.setToken(null);
        }

        return configuration;
    }

    public HueConfiguration requestToken(HueConfiguration configuration) throws HueButtonNotPressedException {
        return hueHttpApi.requestToken(configuration);
    }

    private String getIpFromMDnsService() {
        if (hueServiceResolver.isServiceResolved()) {
            log.debug("automatically discovered Hue IP: {}", hueServiceResolver.getHueIp());
            return hueServiceResolver.getHueIp();
        }
        return null;
    }
}
