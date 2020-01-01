package com.upgrad.quora.service.constants;

public enum TokenPrefixes {
    BearerToken("Bearer "),
    BasicToken("Basic ");

    final String tokenPrefix;
    TokenPrefixes(final String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getTokenPrefix(){
        return this.tokenPrefix;
    }
}
