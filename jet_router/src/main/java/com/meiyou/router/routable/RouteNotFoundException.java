package com.jet.router.routable;

/**
     * Thrown if a given route is not found.
     */
    public  class RouteNotFoundException extends RuntimeException {
        private static final long serialVersionUID = -2278644339983544651L;

        public RouteNotFoundException(String message) {
            super(message);
        }
    }