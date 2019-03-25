package jcescudero15.tuenti.es.mytuentiapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.DeleteUser;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.GetFavoritesUsers;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.SetUserFavorite;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.FavoritesUserContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.presenter.FavoritesUsersPresenter;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class FavoritesUsersPresenterTest {

    @Mock
    private FavoritesUserContract.View mView;

    @Mock
    private DeleteUser mDeleteUser;

    @Mock
    private SetUserFavorite mSetUserFavorite;

    @Mock
    private GetFavoritesUsers mGetFavoritesUsers;


    private FavoritesUsersPresenter mPresenter;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new FavoritesUsersPresenter(mDeleteUser, mSetUserFavorite, mGetFavoritesUsers);
        mPresenter.takeView(mView);
        verify(mGetFavoritesUsers).execute(any(), any());
    }

    @Test
    public void testGetCloseUsers() {
        mPresenter.getFavoritesUsers();
        verify(mGetFavoritesUsers).execute(any(), any());
    }

    @Test
    public void testDeleteUser() {
        mPresenter.deleteUser(new UserViewModel());
        verify(mDeleteUser).execute(any(), any());
    }

    @Test
    public void testUserFavorite() {
        mPresenter.setUserFavorite(true, new UserViewModel());
        verify(mSetUserFavorite).execute(any(), any());
    }
}
