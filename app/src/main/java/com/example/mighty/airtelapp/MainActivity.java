package com.example.mighty.airtelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mighty.airtelapp.data.DataDbHelper;

public class MainActivity extends AppCompatActivity {

    //Database helper that will provide access to the database
    private DataDbHelper mDbHelper;

//    RecyclerView recyclerView;
//    //Adapter
//    List<User> userList;
//    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        recyclerView = findViewById(R.id.recycler_view);
//        adapter = new UserAdapter(this, mInflater);
//        recyclerView.setAdapter(adapter);
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateUser.class);
                startActivity(intent);
            }
        });

        //To access our database, we instantiate our subclass of SQLiteOpener
        //and pass the context, which is the current activity
        mDbHelper = new DataDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
