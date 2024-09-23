package de.mayer.penandpaperdmhelper.setup.service;

import de.mayer.penandpaperdmhelper.setup.actions.HueSetup;
import de.mayer.penandpaperdmhelper.setup.model.HueConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HueService {
    private static final Logger log = LogManager.getLogger(HueService.class);
    private HueServiceResolver hueServiceResolver;
    private final HueHttpApi hueHttpApi;

    public HueService(HueServiceResolver hueServiceResolver, HueHttpApi hueHttpApi) {
        this.hueServiceResolver = hueServiceResolver;
        this.hueHttpApi = hueHttpApi;
    }

    public HueConfiguration checkHueOk(Optional<String> cookieHueIp, Optional<String> token) {
        HueConfiguration configuration = new HueConfiguration();

        var hueIp = cookieHueIp.map((cookie) -> {
            HueSetup.log.debug("Hue IP from cookie: {}", cookie);
            return cookie;
        }).orElseGet(this::getIpFromMDnsService);

        if (hueIp == null || hueIp.isEmpty()) {
            hueIp = getIpFromMDnsService();
        }



        return configuration;
    }

    private String getIpFromMDnsService() {
        if (hueServiceResolver.isServiceResolved()) {
            log.debug("automatically discovered Hue IP: {}", hueServiceResolver.getHueIp());
            return hueServiceResolver.getHueIp();
        }
        return null;
    }
}
