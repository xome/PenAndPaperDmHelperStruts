package de.mayer.penandpaperdmhelper.hue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;


class HueHttpApiTest {

    @Test
    @DisplayName("""
            Given Hue /api reesponses with an Error
            When the objectmapper is used to cast the JSON to a ApiErrorResponse
            Then an object is created""")
    void requestTokenCanParseErrorResponse() {

    }

}