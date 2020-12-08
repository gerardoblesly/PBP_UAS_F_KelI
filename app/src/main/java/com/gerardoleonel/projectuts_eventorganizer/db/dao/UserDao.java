package com.gerardoleonel.projectuts_eventorganizer.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gerardoleonel.projectuts_eventorganizer.db.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User where username=:username AND password=:password Limit 1")
    User findUserById(String username, String password);

    @Query("SELECT * FROM User where username=:username Limit 1")
    User findUserByIdTok(String username);

    @Query("SELECT * FROM User where username=:username Limit 1")
    List<User> UserUnique(String username);


    @Insert
    void createUser(User user);

    @Update
    void updateUser(User user);
}
