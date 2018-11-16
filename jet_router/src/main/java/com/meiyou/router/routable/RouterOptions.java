package com.jet.router.routable;

import android.app.Activity;

import java.util.Map;

/**
     * The class used to determine behavior when opening a URL.
     * If you want to extend Routable to handle things like transition
     * animations or fragments, this class should be augmented.
     */
    public  class RouterOptions {
        Class<? extends Activity> _klass;
        RouterCallback _callback;
        Map<String, String> _defaultParams;

        public RouterOptions() {

        }

        public RouterOptions(Class<? extends Activity> klass) {
            this.setOpenClass(klass);
        }

        public RouterOptions(Map<String, String> defaultParams) {
            this.setDefaultParams(defaultParams);
        }

        public RouterOptions(Map<String, String> defaultParams, Class<? extends Activity> klass) {
            this.setDefaultParams(defaultParams);
            this.setOpenClass(klass);
        }

        public void setOpenClass(Class<? extends Activity> klass) {
            this._klass = klass;
        }

        public Class<? extends Activity> getOpenClass() {
            return this._klass;
        }

        public RouterCallback getCallback() {
            return this._callback;
        }

        public void setCallback(RouterCallback callback) {
            this._callback = callback;
        }

        public void setDefaultParams(Map<String, String> defaultParams) {
            this._defaultParams = defaultParams;
        }

        public Map<String, String> getDefaultParams() {
            return this._defaultParams;
        }
    }