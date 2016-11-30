package com.vedmedenko.chat.injection.components;

import dagger.Component;
import com.vedmedenko.chat.injection.PerActivity;
import com.vedmedenko.chat.injection.modules.ActivityModule;
import com.vedmedenko.chat.ui.activities.MainActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}

