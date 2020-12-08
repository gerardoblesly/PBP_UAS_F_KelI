package com.gerardoleonel.projectuts_eventorganizer.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPackage;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPlace;

import java.util.List;

@Dao
public interface EventPlaceDao {
    @Query("SELECT * FROM EventPlace")
    List<EventPlace> getAllEventPlace();

    @Insert
    void insertEventPlace(List<EventPlace> events);
}
