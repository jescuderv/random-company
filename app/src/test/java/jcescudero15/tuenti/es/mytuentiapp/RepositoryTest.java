package jcescudero15.tuenti.es.mytuentiapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import jcescudero15.tuenti.es.mytuentiapp.data.UserDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.local.UserLocalDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.remote.UserRemoteDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.repository.UserRepository;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoryTest {

    @Mock
    private UserRemoteDataSource mRemoteDataSource;

    @Mock
    private UserLocalDataSource mLocalDataSource;


    private UserDataSource mRepository;
    private static List<User> FAKE_USERS = new ArrayList<>();


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mRepository = new UserRepository(mRemoteDataSource, mLocalDataSource);
        createFakeUsers();
    }

    private void createFakeUsers() {
        User user = new User();
        user.setId("0");
        user.setName("Jose");
        user.setLastName("Escudero");
        user.setFavorite(false);

        User user1 = new User();
        user1.setId("1");
        user1.setName("Pedro");
        user1.setLastName("Garcia");
        user1.setFavorite(false);

        User user2 = new User();
        user2.setId("2");
        user2.setName("Emilio");
        user2.setLastName("Gomez");
        user2.setFavorite(false);

        FAKE_USERS.add(user);
        FAKE_USERS.add(user1);
        FAKE_USERS.add(user2);
    }


    @Test
    public void addUserTest() {
        mRepository.addUsers(FAKE_USERS);
        verify(mLocalDataSource).addUsers(FAKE_USERS);
    }

    @Test
    public void getUserListTest() {
        when(mRepository.getUserList()).thenReturn(Observable.just(FAKE_USERS));

        mRepository.getUserList();
        verify(mLocalDataSource).getUserList();
    }

    @Test
    public void deleteUserTest() {
        mRepository.deleteUser(FAKE_USERS.get(0));
        verify(mLocalDataSource).deleteUser(FAKE_USERS.get(0));
    }

    @Test
    public void setUserFavoriteTest() {
        mRepository.setUserFavorite(true, FAKE_USERS.get(1));
        verify(mLocalDataSource).setUserFavorite(true, FAKE_USERS.get(1));
    }


}
