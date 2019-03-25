package jcescudero15.tuenti.es.mytuentiapp.data.mapper;

import java.util.ArrayList;
import java.util.List;

import jcescudero15.tuenti.es.mytuentiapp.data.local.entity.UserEntity;
import jcescudero15.tuenti.es.mytuentiapp.data.remote.dto.UserDTO;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;

public class UserMapper {

    public static User transform(UserDTO.Result dto) {
        User user = new User();

        user.setId(dto.getLogin().getUuid());
        user.setName(dto.getName().getFirst());
        user.setLastName(dto.getName().getLast());
        user.setProfilePhotoURL(dto.getPicture().getMedium());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setGender(dto.getGender());
        user.setRegisteredDate(dto.getRegistered().getDate());
        user.setStreetLocation(dto.getLocation().getStreet());
        user.setCityLocation(dto.getLocation().getCity());
        user.setStateLocation(dto.getLocation().getState());
        user.setFavorite(false);
        user.setLongitude(Double.parseDouble(dto.getLocation().getCoordinates().getLongitude()));
        user.setLatitude(Double.parseDouble(dto.getLocation().getCoordinates().getLatitude()));

        return user;
    }

    public static UserEntity transform(User user) {
        UserEntity userEntity = new UserEntity(user.getId());

        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setLastName(user.getLastName());
        userEntity.setProfilePhotoURL(user.getProfilePhotoURL());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        userEntity.setRegisteredDate(user.getRegisteredDate());
        userEntity.setStreetLocation(user.getStreetLocation());
        userEntity.setCityLocation(user.getCityLocation());
        userEntity.setStateLocation(user.getStateLocation());
        userEntity.setFavorite(user.isFavorite());
        userEntity.setLongitude(user.getLongitude());
        userEntity.setLatitude(user.getLatitude());

        return userEntity;
    }

    public static User transform(UserEntity userEntity) {
        User user = new User();

        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setLastName(userEntity.getLastName());
        user.setProfilePhotoURL(userEntity.getProfilePhotoURL());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        user.setGender(userEntity.getGender());
        user.setRegisteredDate(userEntity.getRegisteredDate());
        user.setStreetLocation(userEntity.getStreetLocation());
        user.setCityLocation(userEntity.getCityLocation());
        user.setStateLocation(userEntity.getStateLocation());
        user.setFavorite(userEntity.isFavorite());
        user.setLongitude(userEntity.getLongitude());
        user.setLatitude(userEntity.getLatitude());

        return user;
    }

    public static List<User> transform(UserDTO userDTO) {
        List<User> userList = new ArrayList<>();
        for (UserDTO.Result userDto : userDTO.getResults()) {
            userList.add(UserMapper.transform(userDto));
        }
        return userList;
    }

}
