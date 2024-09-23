package de.mayer.penandpaperdmhelper.hue.model.api;

public record ApiRequest(String devicetype, boolean generateclientkey) implements HueApiObject {
}
