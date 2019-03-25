package jcescudero15.tuenti.es.mytuentiapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import jcescudero15.tuenti.es.mytuentiapp.data.local.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
