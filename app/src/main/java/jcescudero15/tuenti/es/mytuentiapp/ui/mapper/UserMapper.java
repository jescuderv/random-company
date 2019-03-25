package jcescudero15.tuenti.es.mytuentiapp.ui.mapper;

import java.util.ArrayList;
import java.util.List;

import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;

public class UserMapper {

    public static UserViewModel transform(User user) {
        UserViewModel userViewModel = new UserViewModel();

        userViewModel.setId(user.getId());
        userViewModel.setName(user.getName());
        userViewModel.setLastName(user.getLastName());
        userViewModel.setProfilePhotoURL(user.getProfilePhotoURL());
        userViewModel.setEmail(user.getEmail());
        userViewModel.setPhone(user.getPhone());
        userViewModel.setGender(user.getGender());
        userViewModel.setRegisteredDate(user.getRegisteredDate());
        userViewModel.setStreetLocation(user.getStreetLocation());
        userViewModel.setCityLocation(user.getCityLocation());
        userViewModel.setStateLocation(user.getStateLocation());
        userViewModel.setFavorite(user.isFavorite());
        userViewModel.setLongitude(user.getLongitude());
        userViewModel.setLatitude(user.getLatitude());

        return userViewModel;
    }

    public static User transform(UserViewModel userViewModel) {
        User user = new User();

        user.setId(userViewModel.getId());
        user.setName(userViewModel.getName());
        user.setLastName(userViewModel.getLastName());
        user.setProfilePhotoURL(userViewModel.getProfilePhotoURL());
        user.setEmail(userViewModel.getEmail());
        user.setPhone(userViewModel.getPhone());
        user.setGender(userViewModel.getGender());
        user.setRegisteredDate(userViewModel.getRegisteredDate());
        user.setStreetLocation(userViewModel.getStreetLocation());
        user.setCityLocation(userViewModel.getCityLocation());
        user.setStateLocation(userViewModel.getStateLocation());
        user.setFavorite(userViewModel.isFavorite());
        user.setLongitude(userViewModel.getLongitude());
        user.setLatitude(userViewModel.getLatitude());

        return user;
    }

    public static List<UserViewModel> transform(List<User> userList) {
        List<UserViewModel> userViewModelList = new ArrayList<>();
        for (User user : userList) {
            userViewModelList.add(transform(user));
        }
        return userViewModelList;
    }

    public static List<User> transformViewModel(List<UserViewModel> userViewModelList) {
        List<User> userList = new ArrayList<>();
        for (UserViewModel userViewModel : userViewModelList) {
            userList.add(transform(userViewModel));
        }
        return userList;
    }
}
