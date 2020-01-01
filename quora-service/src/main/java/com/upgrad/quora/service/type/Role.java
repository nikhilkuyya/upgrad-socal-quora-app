package com.upgrad.quora.service.type;

import java.util.HashMap;
import java.util.Map;

public enum Role {

    Admin("admin"), NonAdmin("nonadmin");
    private final String value;

    Role(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, Role> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (Role env : Role.values()) {
            lookup.put(env.getValue(), env);
        }
    }

    //This method can be used for reverse lookup purpose
    public static Role get(String url) {
        return lookup.get(url);
    }
}

