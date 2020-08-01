package com.example.harihara_medicals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.harihara_medicals.Adapters.SearchProAdapter;

public class SearchProducts extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    SearchProAdapter searchProAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        searchView = findViewById(R.id.searchviewprd);
        recyclerView = findViewById(R.id.searchMedrecy);

    }
}
