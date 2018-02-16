package com.telstra.telstraexcercise.business.di.component;

import com.telstra.telstraexcercise.business.di.module.FactsModule;
import com.telstra.telstraexcercise.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sopan on 13-02-18.
 */

@Singleton
@Component(modules = FactsModule.class)
public interface FactsComponent {
    void inject(MainActivity mainActivity);
}
