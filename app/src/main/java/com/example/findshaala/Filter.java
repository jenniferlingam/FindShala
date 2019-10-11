package com.example.findshaala;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class Filter extends AppCompatActivity{

    private Spinner spinnerstate, spinnercity;
     Button btnfind;
//    String[] statename={"Maharashtra","Telangana","Karnataka","Delhi","Gujarat"};
//    String[] cityname={"Mumbai","Pune","Hyderabad","Vishakapatnam","Banglore","New Delhi","Surat","Ahemdabad"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        addItemsOnSpinnercity();
        addItemsOnSpinnerstate();
        addListenerOnSpinnerItemSelection();
        btnfind = findViewById(R.id.find);

        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),tab.class);
                startActivity(intent);
            }
        });

    }


    public void addItemsOnSpinnercity() {

        spinnercity = findViewById(R.id.spinnercity);

        List<String> list = new ArrayList<String>();
        list.add("None");
        list.add("Mumbai");
        list.add("Hyderabad");
        list.add("Vishakapatnam");
        list.add("Banglore");
        list.add("New Delhi");
        list.add("Surat");
        list.add("Ahemdabad");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercity.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinnerstate() {

        spinnerstate = findViewById(R.id.spinnerstate);
        List<String> list = new ArrayList<String>();
        list.add("None");
        list.add("Maharashtra");
        list.add("Telangana");
        list.add("Karnataka");
        list.add("Delhi");
        list.add("Gujarat");
//        list.add("Surat");
//        list.add("Ahemdabad");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstate.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinnerstate=findViewById(R.id.spinnerstate);
        spinnerstate.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

}
