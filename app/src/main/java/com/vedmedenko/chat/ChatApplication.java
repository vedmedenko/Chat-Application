package com.vedmedenko.chat;

import android.app.Application;
import android.content.Context;


import java.lang.ref.WeakReference;

import timber.log.Timber;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.vedmedenko.chat.injection.components.ApplicationComponent;
import com.vedmedenko.chat.injection.components.DaggerApplicationComponent;
import com.vedmedenko.chat.injection.modules.ApplicationModule;

public class ChatApplication extends Application {

    private ApplicationComponent applicationComponent;
    private RefWatcher refWatcher;

    public static com.vedmedenko.chat.ChatApplication get(WeakReference<Context> contextWeakReference) {
        return (com.vedmedenko.chat.ChatApplication) contextWeakReference.get().getApplicationContext();
    }

    public static RefWatcher getRefWatcher(Context context) {
        return ((com.vedmedenko.chat.ChatApplication) context.getApplicationContext()).refWatcher;
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
        initLeakCanary();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initLeakCanary() {
        refWatcher = LeakCanary.install(this);
    }
}
