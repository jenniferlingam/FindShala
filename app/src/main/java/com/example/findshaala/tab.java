package com.example.findshaala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;


public class tab extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;

    private ViewPageAdapter adapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.pager);
        adapter=new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout=findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

    }
}