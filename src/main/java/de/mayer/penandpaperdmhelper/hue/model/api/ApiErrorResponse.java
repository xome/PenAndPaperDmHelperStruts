package de.mayer.penandpaperdmhelper.hue.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiErrorResponse(@JsonProperty("error") ApiError error) {
}
