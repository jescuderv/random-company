package jcescudero15.tuenti.es.mytuentiapp.ui.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import jcescudero15.tuenti.es.mytuentiapp.data.exception.NetworkErrorException;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.DeleteUser;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.GetUserList;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.RetrieveRandomUsers;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.SetUserFavorite;
import jcescudero15.tuenti.es.mytuentiapp.ui.UserFilterSort;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.AllUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.mapper.UserMapper;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;
import jcescudero15.tuenti.es.mytuentiapp.utils.di.scopes.ActivityScoped;

@ActivityScoped
public class AllUsersPresenter implements AllUsersContract.Presenter {

    private AllUsersContract.View mView;

    private GetUserList mGetUserList;
    private RetrieveRandomUsers mRetrieveRandomUsers;
    private DeleteUser mDeleteUser;
    private SetUserFavorite mSetUserFavorite;


    @Inject
    public AllUsersPresenter(GetUserList getUserList, RetrieveRandomUsers retrieveRandomUsers,
                      DeleteUser deleteUser, SetUserFavorite setUserFavorite) {
        mGetUserList = getUserList;
        mRetrieveRandomUsers = retrieveRandomUsers;
        mDeleteUser = deleteUser;
        mSetUserFavorite = setUserFavorite;
    }


    @Override
    public void getUserList(UserFilterSort sorted, String filterQuery) {
        mGetUserList.execute(new DisposableObserver<List<User>>() {
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
        }, new GetUserList.Params(sorted, filterQuery));
    }

    @Override
    public void retrieveRandomUsers(Integer results) {
        mView.showProgress();
        mRetrieveRandomUsers.execute(new DisposableObserver<List<User>>() {
            @Override
            public void onNext(List<User> users) {
                // Nothing to do
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                if (e instanceof NetworkErrorException)
                    mView.showNetworkErrorMessage();
                else mView.showDatabaseErrorMessage();
            }

            @Override
            public void onComplete() {
                mView.hideProgress();
            }
        }, new RetrieveRandomUsers.Params(results));
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
    public void takeView(AllUsersContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mGetUserList.dispose();
        mRetrieveRandomUsers.dispose();
        mDeleteUser.dispose();
        mSetUserFavorite.dispose();
        mView = null;
    }

}
