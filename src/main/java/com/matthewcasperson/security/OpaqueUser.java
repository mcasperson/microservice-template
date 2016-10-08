package com.matthewcasperson.security;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

/**
 * An Elide user that contains the details we need to match before returning a result
 */
public class OpaqueUser {
    private final String secret;

    public String getSecret() {
        return secret;
    }

    public OpaqueUser(final String secret) {
        checkArgument(StringUtils.isNotBlank(secret));

        this.secret = secret;
    }
}
