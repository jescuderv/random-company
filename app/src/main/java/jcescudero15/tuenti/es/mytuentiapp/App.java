package jcescudero15.tuenti.es.mytuentiapp;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import jcescudero15.tuenti.es.mytuentiapp.utils.di.DaggerAppComponent;

public class App extends DaggerApplication{

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}
