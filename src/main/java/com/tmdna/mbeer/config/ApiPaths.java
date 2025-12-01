package com.tmdna.mbeer.config;

public final class ApiPaths {

    private ApiPaths() {
    }

    public static final String BASE = "/api/v1";
    public static final String ID = "{id}";

    public static final class Beer {
        private Beer() {
        }

        public static final String BASE = ApiPaths.BASE + "/beer";
        public static final String WITH_ID = getPathWithId(BASE);
    }

    public static final class Customer {
        private Customer() {
        }

        public static final String BASE = ApiPaths.BASE + "/customer";
        public static final String WITH_ID = getPathWithId(BASE);
    }

    private static String getPathWithId(String base) {
        return base + "/" + ApiPaths.ID;
    }
}
