package com.jet.router.routable;

/**
     * The class used when you want to map a function (given in `run`)
     * to a Router URL.
     */
    public  abstract class RouterCallback {
        public abstract void run(RouteContext context);
    }