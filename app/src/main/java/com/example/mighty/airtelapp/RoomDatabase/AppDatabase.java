//package com.example.mighty.airtelapp;
//
//import android.arch.persistence.db.SupportSQLiteDatabase;
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.Room;
//import android.arch.persistence.room.RoomDatabase;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.support.annotation.NonNull;
//
////Room database
//@Database(entities = {User.class}, version = 1)
//public abstract class AppDatabase extends RoomDatabase {
//    public abstract UserDao userDao();
//
//    //Make the AppDatabase a singleton to prevent having multiple instances of the
//    //database opened at the same time
//    private static AppDatabase INSTANCE;
//
//    static AppDatabase getDatabase(final Context context){
//        if(INSTANCE == null){
//            synchronized (AppDatabase.class){
//                if (INSTANCE == null){
//
//                    //Add the code to get a database. This code uses Room's database builder to create a
//                    //RoomDatabase object in the application context from the AppDatabase class and names it
//                    // "user_database"
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, "user_database")
//                        // Wipes and rebuilds instead of migrating if no Migration object.
//                            .fallbackToDestructiveMigration()
//                            .addCallback(sRoomDatabaseCallback)
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//
//    //Override the onOpen method to populate the database.
//    // For this sample, we clear the database every time it is created or opened.
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
//
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            // If you want to keep the data through app restarts,
//            // comment out the following line.
//            new PopulateDbAsync(INSTANCE).execute();
//        }
//    };
//
//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final UserDao mDao;
//
//        PopulateDbAsync(AppDatabase db){
//            mDao = db.userDao();
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate on creation.
//            mDao.deleteAll();
//
////            User user = new User("Hello");
////            mDao.insertAll(user);
////            user = new User("World");
////            mDao.insertAll(user);
//            return null;
//        }
//    }
//}
