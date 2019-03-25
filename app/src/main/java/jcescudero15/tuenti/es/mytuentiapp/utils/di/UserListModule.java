package jcescudero15.tuenti.es.mytuentiapp.utils.di;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.AllUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.CloseUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.FavoritesUserContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.fragment.AllUsersFragment;
import jcescudero15.tuenti.es.mytuentiapp.ui.fragment.CloseUsersFragment;
import jcescudero15.tuenti.es.mytuentiapp.ui.fragment.FavoritesUserFragment;
import jcescudero15.tuenti.es.mytuentiapp.ui.presenter.AllUsersPresenter;
import jcescudero15.tuenti.es.mytuentiapp.ui.presenter.CloseUsersPresenter;
import jcescudero15.tuenti.es.mytuentiapp.ui.presenter.FavoritesUsersPresenter;
import jcescudero15.tuenti.es.mytuentiapp.utils.di.scopes.ActivityScoped;
import jcescudero15.tuenti.es.mytuentiapp.utils.di.scopes.FragmentScoped;

@Module
abstract class UserListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AllUsersFragment allUsersFragment();

    @ActivityScoped
    @Binds
    abstract AllUsersContract.Presenter provideAllUsersPresenter(AllUsersPresenter presenter);


    @FragmentScoped
    @ContributesAndroidInjector
    abstract FavoritesUserFragment favoritesUserFragment();

    @ActivityScoped
    @Binds
    abstract FavoritesUserContract.Presenter provideFavoritesPresenter(FavoritesUsersPresenter presenter);


    @FragmentScoped
    @ContributesAndroidInjector
    abstract CloseUsersFragment closeUsersFragment();

    @ActivityScoped
    @Binds
    abstract CloseUsersContract.Presenter provideCloseUsersPresenter(CloseUsersPresenter presenter);
}
