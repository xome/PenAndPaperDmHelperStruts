package de.mayer.penandpaperdmhelper.hue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jmdns.*;
import java.io.IOException;

@Service
@Scope("singleton")
public class HueServiceResolver implements ServiceTypeListener {

    private static final Logger log = LogManager.getLogger(HueServiceResolver.class);
    private String hueIp;
    private JmDNS jmdns;
    private boolean resolved;

    @PostConstruct
    private void postConstruct() {
        try {
            jmdns = JmDNS.create();
            // Try to resolve Hue Bridge IP with mdns
            // see https://developers.meethue.com/develop/application-design-guidance/hue-bridge-discovery/
            jmdns.addServiceTypeListener(this);
        } catch (IOException e) {
            throw new RuntimeException("Cannot init JmDNS!", e);
        }
        log.traceExit(hueIp);
    }

    @PreDestroy
    private void preDestroy() throws IOException {
        this.jmdns.close();
    }

    public boolean isServiceResolved() {
        return this.resolved;
    }

    public String getHueIp() {
        return hueIp;
    }

    @Override
    public void serviceTypeAdded(ServiceEvent event) {
        if ("_hue._tcp.local.".equals(event.getType())){
            log.traceEntry(() -> event);
            var serviceList = jmdns.list(event.getType(), 0);
            if (serviceList == null || serviceList.length == 0) {
                this.resolved = false;
                log.trace("Could not resolve Hue Bridge Service!");
                return;
            }
            hueIp = serviceList[0].getInet4Addresses()[0].getHostAddress();
            this.resolved = true;
            log.trace("Hue Service resolved: {}", hueIp);
        }
    }

    @Override
    public void subTypeForServiceTypeAdded(ServiceEvent event) {
        log.traceEntry(() -> event);
    }
}
