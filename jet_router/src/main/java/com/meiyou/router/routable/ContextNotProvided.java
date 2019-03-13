package com.jet.router.routable;

/**
 * Thrown if no context has been found.
 */
public class ContextNotProvided extends RuntimeException {
    private static final long serialVersionUID = -1381427067387547157L;

    public ContextNotProvided(String message) {
        super(message);
    }
}