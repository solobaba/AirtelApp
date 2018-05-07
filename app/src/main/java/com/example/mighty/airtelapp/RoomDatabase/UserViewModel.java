//package com.example.mighty.airtelapp;
//
//import android.app.Application;
//import android.arch.lifecycle.AndroidViewModel;
//import android.arch.lifecycle.LiveData;
//
//import java.util.List;
//
//public class UserViewModel extends AndroidViewModel {
//
//    //Add a private member variable to hold a reference to the repository
//
//    //Add a private LiveData member variable to cache the list of words.
//    private UserRepository mRepository;
//    private LiveData<List<User>> AllUsers;
//
//    //Add a constructor that gets a reference to the repository
//    //and gets the list of words from the repository.
//    public UserViewModel(Application application) {
//        super(application);
//        mRepository = new UserRepository(application);
//        AllUsers = mRepository.getAllUsers();
//    }
//
//    //Add a "getter" method for all the words. This completely hides the
//    // implementation from the UI.
//    LiveData<List<User>> getmAllUsers(){
//        return AllUsers;
//    }
//
//    //Create a wrapper insert() method that calls the Repository's insert() method.
//    // In this way, the implementation of insert() is completely hidden from the UI.
//    public void insert(User user){
//        mRepository.insert(user);
//    }
//}
