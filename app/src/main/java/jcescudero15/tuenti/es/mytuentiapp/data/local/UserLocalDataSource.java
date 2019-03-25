package jcescudero15.tuenti.es.mytuentiapp.data.local;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import jcescudero15.tuenti.es.mytuentiapp.data.UserDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.exception.DatabaseErrorException;
import jcescudero15.tuenti.es.mytuentiapp.data.local.entity.UserEntity;
import jcescudero15.tuenti.es.mytuentiapp.data.mapper.UserMapper;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;

@Singleton
public class UserLocalDataSource implements UserDataSource {

    private UserDao mUserDao;


    @Inject
    UserLocalDataSource(UserDao userDao) {
        mUserDao = userDao;
    }


    @Override
    public Observable<List<User>> retrieveUserList(Integer results) {
        // Retrieve user list is handled by remote data source
        return Observable.empty();
    }

    @Override
    public Observable<List<User>> getUserList() {
        return mUserDao.getUsers()
                .doOnError(throwable -> {
                    throw new DatabaseErrorException();
                })
                .map(userEntities -> {
                    List<User> userList = new ArrayList<>();
                    for (UserEntity userEntity : userEntities) {
                        userList.add(UserMapper.transform(userEntity));
                    }
                    return userList;
                })
                .toObservable();
    }

    @Override
    public void addUsers(List<User> userList) {
        for (User user : userList) {
            mUserDao.addUser(UserMapper.transform(user));
        }
    }

    @Override
    public Completable deleteUser(User user) {
        return Completable.create(emitter -> {
            int affectedRows = mUserDao.deleteUser(UserMapper.transform(user));
            if (affectedRows > 0) emitter.onComplete();
            else emitter.onError(new DatabaseErrorException());
        });
    }

    @Override
    public Completable setUserFavorite(boolean isFavorite, User user) {
        return Completable.create(emitter -> {
            int affectedRows = mUserDao.setUserFavorite(isFavorite, UserMapper.transform(user).getId());
            if (affectedRows > 0) emitter.onComplete();
            else emitter.onError(new DatabaseErrorException());
        });
    }
}
