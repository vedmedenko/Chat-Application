package com.vedmedenko.chat.injection.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vedmedenko.chat.injection.ApplicationContext;
import com.vedmedenko.chat.utils.ConstantsManager;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    DatabaseReference provideDatabaseReference(@NonNull FirebaseDatabase firebaseDatabase) {
        return firebaseDatabase.getReference();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@NonNull @ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
