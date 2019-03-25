package jcescudero15.tuenti.es.mytuentiapp.data.remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import jcescudero15.tuenti.es.mytuentiapp.data.UserDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.exception.NetworkErrorException;
import jcescudero15.tuenti.es.mytuentiapp.data.mapper.UserMapper;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;

@Singleton
public class UserRemoteDataSource implements UserDataSource {

    private EndPointInterface mEndpointInterface;


    @Inject
    UserRemoteDataSource(EndPointInterface endPointInterface) {
        mEndpointInterface = endPointInterface;
    }


    @Override
    public Observable<List<User>> retrieveUserList(Integer results) {
        return mEndpointInterface.getRandomUserList(results)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(new NetworkErrorException());
                })
                .map(UserMapper::transform);
    }

    @Override
    public Observable<List<User>> getUserList() {
        // Get user list is handled by local data source
        return Observable.empty();
    }

    @Override
    public void addUsers(List<User> userList) {
        // Add users is handled by local data source
    }

    @Override
    public Completable deleteUser(User user) {
        // Delete user is handled by local data source
        return Completable.complete();
    }

    @Override
    public Completable setUserFavorite(boolean isFavorite, User user) {
        // Set user favorite is handled by local data source
        return Completable.complete();
    }
}
