package com.example.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> courcename = new ArrayList<>();
    ArrayList<String> courceexplination = new ArrayList<>();
    ArrayList<Integer> pictures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler);

        courcename.add("Spinner");
        courcename.add("Toggle Button");
        courcename.add("CheckBox");

        courceexplination.add("Spinner u know it , the drop down menue thingi");
        courceexplination.add("ON/OFF");
        courceexplination.add("just a simple checkbox nothig else");

        pictures.add(R.drawable.a);
        pictures.add(R.drawable.b);
        pictures.add(R.drawable.c);

        recycleradapter recycleradapter = new recycleradapter(courcename, courceexplination, pictures, this);
        recyclerView.setAdapter(recycleradapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}