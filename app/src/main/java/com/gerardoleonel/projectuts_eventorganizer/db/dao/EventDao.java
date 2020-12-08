package com.gerardoleonel.projectuts_eventorganizer.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventType;

import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM EventType")
    List<EventType> getAllEvents();

    @Insert
    void insertEventType(List<EventType> events);
}
