package jcescudero15.tuenti.es.mytuentiapp.ui.contract;

import java.util.List;

import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;
import jcescudero15.tuenti.es.mytuentiapp.utils.BasePresenter;

public interface FavoritesUserContract {

    interface View {
        void showUserList(List<UserViewModel> userList);

        void showEmptyUsersMessage();

        void showNetworkErrorMessage();

        void showDatabaseErrorMessage();

        void showUserDeletedMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void getFavoritesUsers();

        void deleteUser(UserViewModel userViewModel);

        void setUserFavorite(boolean isFavorite, UserViewModel userViewModel);
    }
}
