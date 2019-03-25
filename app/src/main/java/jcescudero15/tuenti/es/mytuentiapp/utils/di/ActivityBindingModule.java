package jcescudero15.tuenti.es.mytuentiapp.utils.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import jcescudero15.tuenti.es.mytuentiapp.ui.activity.UserListActivity;
import jcescudero15.tuenti.es.mytuentiapp.utils.di.scopes.ActivityScoped;

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = UserListModule.class)
    abstract UserListActivity userListActivity();

}
