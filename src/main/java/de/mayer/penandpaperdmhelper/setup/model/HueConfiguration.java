package de.mayer.penandpaperdmhelper.setup.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class HueConfiguration {

    private String ip;
    private String token;

    public HueConfiguration() {
    }

    public String getIp() {
        return ip;
    }

    public String getToken() {
        return token;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HueConfiguration that = (HueConfiguration) o;
        return Objects.equals(ip, that.ip) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, token);
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ip", ip)
                .append("token", token)
                .toString();
    }
}
