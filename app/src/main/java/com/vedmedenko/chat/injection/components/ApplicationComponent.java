package com.vedmedenko.chat.injection.components;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

import javax.inject.Singleton;

import dagger.Component;

import com.google.firebase.database.DatabaseReference;
import com.vedmedenko.chat.injection.ApplicationContext;
import com.vedmedenko.chat.injection.modules.ApplicationModule;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    DatabaseReference databaseReference();

    SharedPreferences sharedPreferences();
}