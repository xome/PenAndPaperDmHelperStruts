package de.mayer.penandpaperdmhelper;

public enum CookieKeys {

    HueToken {
        @Override
        public String toString() {
            return "hueToken";
        }
    },

    HueIp {
        @Override
        public String toString() {
            return "hueIp";
        }
    }

}
