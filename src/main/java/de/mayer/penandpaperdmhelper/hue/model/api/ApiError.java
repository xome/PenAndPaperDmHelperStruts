package de.mayer.penandpaperdmhelper.hue.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiError(@JsonProperty("type") Integer type,
                       @JsonProperty("address") String address,
                       @JsonProperty("description") String description) {
}
