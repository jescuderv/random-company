package jcescudero15.tuenti.es.mytuentiapp.ui.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import jcescudero15.tuenti.es.mytuentiapp.data.exception.NetworkErrorException;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.DeleteUser;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.GetCloseUsers;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.SetUserFavorite;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.CloseUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.mapper.UserMapper;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;

import static jcescudero15.tuenti.es.mytuentiapp.utils.Utils.getCurrentLocation;

@Singleton
public class CloseUsersPresenter implements CloseUsersContract.Presenter {

    private CloseUsersContract.View mView;

    private DeleteUser mDeleteUser;
    private SetUserFavorite mSetUserFavorite;
    private GetCloseUsers mGetCloseUsers;


    @Inject
    public CloseUsersPresenter(DeleteUser deleteUser, SetUserFavorite setUserFavorite,
                        GetCloseUsers getCloseUsers) {
        mDeleteUser = deleteUser;
        mSetUserFavorite = setUserFavorite;
        mGetCloseUsers = getCloseUsers;
    }


    @Override
    public void getCloseUsers() {
        mGetCloseUsers.execute(new DisposableObserver<List<User>>() {
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
        }, new GetCloseUsers.Params(getCurrentLocation()));
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
    public void takeView(CloseUsersContract.View view) {
        mView = view;
        getCloseUsers();
    }

    @Override
    public void dropView() {
        mDeleteUser.dispose();
        mSetUserFavorite.dispose();
        mGetCloseUsers.dispose();
        mView = null;
    }
}
