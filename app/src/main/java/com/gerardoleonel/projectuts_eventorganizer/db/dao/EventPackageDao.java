package com.gerardoleonel.projectuts_eventorganizer.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPackage;

import java.util.List;

@Dao
public interface EventPackageDao {

    @Query("SELECT * FROM EventPackage")
    List<EventPackage> getAllEventPackage();

    @Insert
    void insertEventPackage(List<EventPackage> events);
}