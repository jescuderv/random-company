package jcescudero15.tuenti.es.mytuentiapp.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import jcescudero15.tuenti.es.mytuentiapp.data.UserDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.local.UserLocalDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.remote.UserRemoteDataSource;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;

@Singleton
public class UserRepository implements UserDataSource {

    private UserDataSource mRemoteDataSource;
    private UserDataSource mLocalDataSource;


    @Inject
    public UserRepository(UserRemoteDataSource remoteDataSource, UserLocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    @Override
    public Observable<List<User>> retrieveUserList(Integer results) {
        return mRemoteDataSource.retrieveUserList(results)
                .doOnNext(this::addUsers);
    }

    @Override
    public Observable<List<User>> getUserList() {
        return mLocalDataSource.getUserList();
    }

    @Override
    public void addUsers(List<User> userList) {
        mLocalDataSource.addUsers(userList);
    }

    @Override
    public Completable deleteUser(User user) {
        return mLocalDataSource.deleteUser(user);
    }

    @Override
    public Completable setUserFavorite(boolean isFavorite, User user) {
        return mLocalDataSource.setUserFavorite(isFavorite, user);
    }
}
