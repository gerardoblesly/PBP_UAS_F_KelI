package com.gerardoleonel.projectuts_eventorganizer.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gerardoleonel.projectuts_eventorganizer.db.dao.EventDao;
import com.gerardoleonel.projectuts_eventorganizer.db.dao.EventPackageDao;
import com.gerardoleonel.projectuts_eventorganizer.db.dao.EventPlaceDao;
import com.gerardoleonel.projectuts_eventorganizer.db.dao.UserDao;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPackage;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPlace;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventType;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.User;

@Database(entities = {
        User.class, EventType.class,
        EventPackage.class, EventPlace.class
}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    public abstract UserDao getUserDao();
    public abstract EventDao getEventDao();
    public abstract EventPackageDao getEventPackageDao();
    public abstract EventPlaceDao getEventPlaceDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "EventOrganizer.db").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
