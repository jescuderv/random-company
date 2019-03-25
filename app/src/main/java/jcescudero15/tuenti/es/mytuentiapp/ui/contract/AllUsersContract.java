package jcescudero15.tuenti.es.mytuentiapp.ui.contract;

import android.widget.ImageView;

import java.util.List;

import jcescudero15.tuenti.es.mytuentiapp.ui.UserFilterSort;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;
import jcescudero15.tuenti.es.mytuentiapp.utils.BasePresenter;

public interface AllUsersContract {

    interface View {
        void showUserList(List<UserViewModel> userList);

        void showEmptyUsersMessage();

        void showNetworkErrorMessage();

        void showDatabaseErrorMessage();

        void showUserDeletedMessage();

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends BasePresenter<View> {
        void getUserList(UserFilterSort sorted, String filterQuery);

        void retrieveRandomUsers(Integer results);

        void deleteUser(UserViewModel userViewModel);

        void setUserFavorite(boolean isFavorite, UserViewModel userViewModel);
    }

    interface OnUserSelectedListener {
        void showUserInfo(UserViewModel userViewModel, ImageView profileImage);
    }
}
