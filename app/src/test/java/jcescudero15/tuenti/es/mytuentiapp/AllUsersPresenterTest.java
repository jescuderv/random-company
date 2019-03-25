package jcescudero15.tuenti.es.mytuentiapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.DeleteUser;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.GetUserList;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.RetrieveRandomUsers;
import jcescudero15.tuenti.es.mytuentiapp.domain.usecase.SetUserFavorite;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.AllUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.presenter.AllUsersPresenter;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class AllUsersPresenterTest {

    @Mock
    private AllUsersContract.View mView;

    @Mock
    private GetUserList mGetUserList;

    @Mock
    private RetrieveRandomUsers mRetrieveRandomUsers;

    @Mock
    private DeleteUser mDeleteUser;

    @Mock
    private SetUserFavorite mSetUserFavorite;


    private AllUsersPresenter mPresenter;

//    @Captor
//    private ArgumentCaptor<DisposableObserver<List<User>>> captor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new AllUsersPresenter(mGetUserList, mRetrieveRandomUsers, mDeleteUser, mSetUserFavorite);
        mPresenter.takeView(mView);
    }

    @Test
    public void testDisposeUseCase() {
        mPresenter.dropView();

        verify(mGetUserList).dispose();
        verify(mRetrieveRandomUsers).dispose();
        verify(mDeleteUser).dispose();
        verify(mSetUserFavorite).dispose();
    }

    @Test
    public void testRetrieveRandomUsers() {
        mPresenter.retrieveRandomUsers(10);
        verify(mRetrieveRandomUsers).execute(any(), any());
        verify(mView).showProgress();
    }

    @Test
    public void testGetUserList() {
//        mPresenter.getUserList(UserFilterSort.DEFAULT, "");
//        verify(mGetUserList).execute(captor.capture(), any());
//        DisposableObserver<List<User>> value = captor.getValue();
//        verify(mGetUserList).execute(any(), any());
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