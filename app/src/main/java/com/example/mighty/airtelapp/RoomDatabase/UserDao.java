//package com.example.mighty.airtelapp;
//
//import android.arch.lifecycle.LiveData;
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.Query;
//import android.arch.persistence.room.Update;
//
//import java.util.List;
//
//@Dao
//public interface UserDao {
//    @Query("SELECT * FROM User WHERE id=:userId")
//    LiveData<User> getUserById(int userId);
//
//    @Query("SELECT * FROM User")
//    LiveData<List<User>> getAllUsers();
//
//    @Insert
//    void insertAll(User... users);
//
//    @Update
//    void updateAll(User... users);
//
//    @Query("DELETE FROM user")
//    void deleteAll();
//}
