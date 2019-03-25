package jcescudero15.tuenti.es.mytuentiapp.data;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;

public interface UserDataSource {

    Observable<List<User>> retrieveUserList(Integer results);

    Observable<List<User>> getUserList();

    void addUsers(List<User> userList);

    Completable deleteUser(User user);

    Completable setUserFavorite(boolean isFavorite, User user);
}
