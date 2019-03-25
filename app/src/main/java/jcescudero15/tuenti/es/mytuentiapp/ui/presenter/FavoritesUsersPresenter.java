package jcescudero15.tuenti.es.mytuentiapp.ui.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import jcescudero15.tuenti.es.mytuentiapp.data.exception.NetworkErrorException;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.DeleteUser;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.GetFavoritesUsers;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.SetUserFavorite;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.FavoritesUserContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.mapper.UserMapper;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;
import jcescudero15.tuenti.es.mytuentiapp.utils.di.scopes.ActivityScoped;

@ActivityScoped
public class FavoritesUsersPresenter implements FavoritesUserContract.Presenter {

    private FavoritesUserContract.View mView;

    private DeleteUser mDeleteUser;
    private SetUserFavorite mSetUserFavorite;
    private GetFavoritesUsers mGetFavoritesUsers;


    @Inject
    public FavoritesUsersPresenter(DeleteUser deleteUser, SetUserFavorite setUserFavorite,
                            GetFavoritesUsers getFavoritesUsers) {
        mDeleteUser = deleteUser;
        mSetUserFavorite = setUserFavorite;
        mGetFavoritesUsers = getFavoritesUsers;
    }


    @Override
    public void getFavoritesUsers() {
        mGetFavoritesUsers.execute(new DisposableObserver<List<User>>() {
            @Override
            public void onNext(List<User> userList) {
                mView.showUserList(UserMapper.transform(userList));
                if (userList.size() <= 0) mView.showEmptyUsersMessage();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof NetworkErrorException)
                    mView.showNetworkErrorMessage();
                else mView.showDatabaseErrorMessage();
            }

            @Override
            public void onComplete() {
            }
        }, null);
    }

    @Override
    public void deleteUser(UserViewModel userViewModel) {
        mDeleteUser.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.showUserDeletedMessage();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof NetworkErrorException)
                    mView.showNetworkErrorMessage();
                else mView.showDatabaseErrorMessage();
            }
        }, new DeleteUser.Params(UserMapper.transform(userViewModel)));
    }

    @Override
    public void setUserFavorite(boolean isFavorite, UserViewModel userViewModel) {
        mSetUserFavorite.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                // Nothing to do
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof NetworkErrorException)
                    mView.showNetworkErrorMessage();
                else mView.showDatabaseErrorMessage();
            }
        }, new SetUserFavorite.Params(UserMapper.transform(userViewModel), isFavorite));
    }


    @Override
    public void takeView(FavoritesUserContract.View view) {
        mView = view;
        getFavoritesUsers();
    }

    @Override
    public void dropView() {
        mDeleteUser.dispose();
        mSetUserFavorite.dispose();
        mGetFavoritesUsers.dispose();
        mView = null;
    }
}
