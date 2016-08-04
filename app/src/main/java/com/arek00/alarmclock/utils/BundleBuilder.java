package com.arek00.alarmclock.utils;

import android.os.Bundle;
import android.os.IBinder;

public class BundleBuilder {

    private Bundle bundle = new Bundle();

    public BundleBuilder withString(String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public BundleBuilder withInteger(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public BundleBuilder withBinder(String key, IBinder value) {
        bundle.putBinder(key, value);
        return this;
    }

    public BundleBuilder withDouble(String key, double value) {
        bundle.putDouble(key, value);
        return this;
    }

    public Bundle build() {
        return bundle;
    }

}
