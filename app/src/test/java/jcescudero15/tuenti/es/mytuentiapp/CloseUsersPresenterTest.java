package jcescudero15.tuenti.es.mytuentiapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.DeleteUser;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.GetCloseUsers;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.SetUserFavorite;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.CloseUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.presenter.CloseUsersPresenter;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class CloseUsersPresenterTest {

    @Mock
    private CloseUsersContract.View mView;

    @Mock
    private DeleteUser mDeleteUser;

    @Mock
    private SetUserFavorite mSetUserFavorite;

    @Mock
    private GetCloseUsers mGetCloseUsers;


    private CloseUsersPresenter mPresenter;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new CloseUsersPresenter(mDeleteUser, mSetUserFavorite, mGetCloseUsers);
        mPresenter.takeView(mView);
        verify(mGetCloseUsers).execute(any(), any());
    }

    @Test
    public void testGetCloseUsers() {
        mPresenter.getCloseUsers();
        verify(mGetCloseUsers).execute(any(), any());
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
