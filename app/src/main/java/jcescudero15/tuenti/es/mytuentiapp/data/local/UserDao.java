package jcescudero15.tuenti.es.mytuentiapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import jcescudero15.tuenti.es.mytuentiapp.data.local.entity.UserEntity;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(UserEntity userEntity);

    @Query("SELECT * FROM UserEntity")
    Flowable<List<UserEntity>> getUsers();

    @Delete
    int deleteUser(UserEntity userEntity);

    @Query("UPDATE UserEntity SET is_favorite = :isFavorite WHERE id = :userId")
    int setUserFavorite(boolean isFavorite, String userId);
}
