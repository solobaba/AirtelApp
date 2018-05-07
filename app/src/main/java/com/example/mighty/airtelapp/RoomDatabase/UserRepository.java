//package com.example.mighty.airtelapp;
//
//import android.app.Application;
//import android.arch.lifecycle.LiveData;
//import android.os.AsyncTask;
//
//import java.util.List;
//
//public class UserRepository {
//
//    //Add member variables for the DAO and the list of words
//    private UserDao UserDao;
//    private LiveData<List<User>> AllUsers;
//
//    //Add a constructor that gets a handle to the database and initializes the member variables
//    UserRepository(Application application){
//        AppDatabase db = AppDatabase.getDatabase(application);
//        UserDao = db.userDao();
//        AllUsers = UserDao.getAllUsers();
//    }
//
//    //Add a wrapper for getAllWords(). Room executes all queries on a separate thread.
//    // Observed LiveData will notify the observer when the data has changed
//    LiveData<List<User>> getAllUsers(){
//        return AllUsers;
//    }
//
//    //Add a wrapper for the insert() method. You must call this on a non-UI thread or your app will crash.
//    // Room ensures that you don't do any long-running operations on the main thread, blocking the UI
//    public void insert (User user){
//        new insertAsyncTask(UserDao).execute(user);
//    }
//
//    //AsyncTask code
//    private static class insertAsyncTask extends AsyncTask<User, Void, Void>{
//        private UserDao mAsyncTaskDao;
//        insertAsyncTask(UserDao dao){
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final User... params) {
//            mAsyncTaskDao.insertAll(params[0]);
//            return null;
//        }
//    }
//}
