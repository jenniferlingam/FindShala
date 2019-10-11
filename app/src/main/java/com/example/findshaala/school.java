package com.example.findshaala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class school extends AppCompatActivity {
    SchoolAdapter schoolAdapter;
    ArrayList<Data> schoolList;
    String email="";
    SessionManager sessionManager;
    RecyclerView recyclerView;
    //shimmer
    ProgressBar loading;
    ShimmerFrameLayout mShimmerViewContainer;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        schoolList = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.school);
        schoolList = new ArrayList<>();
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        email = user.get(sessionManager.EMAIL);
        Log.d("email",email);

        recyclerView = findViewById(R.id.rv);
        //loading=findViewById(R.id.loading);


        //shimmer
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvlayoutmanager = linearLayoutManager;
        recyclerView.setLayoutManager(rvlayoutmanager);

        getData();
//        if(foodList.isEmpty()){
//
//            //Toast.makeText(this, "Order Cart Empty", Toast.LENGTH_LONG).show();
//            setContentView(R.layout.error_page);
//
//        }


    }

    //shimmer
    @Override
    protected void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShimmerViewContainer.stopShimmer();
    }
    //end shimmer
    @Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(getApplicationContext(), Filter.class);
        startActivity(intent);
        finish();
        //Toast.makeText(this, "Refresh to Load Items", Toast.LENGTH_SHORT).show();
        return true;
    }


    /////////////////////////////////////////////////////////////////////
    public void getData(){
        getIp ip = new getIp();
        String del = ip.getIp();

        RequestQueue requestQueue = Volley.newRequestQueue(school.this);
        String URL = ""+del+":8080/getSchool";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();
        Log.d("str", "str is" + requestBody);

        ConnectionManager.sendData(requestBody, requestQueue, URL, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.d("result is ", "" + result);
                if(result.equals("not found")){
                    Toast.makeText(school.this, "No data in database :(", Toast.LENGTH_SHORT).show();
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    flag=0;

                }
                if (result != null) {
                    flag=1;
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        Log.d("jsonAray", "" + jsonArray);
                        Log.d("Size of JSON Array", "" + jsonArray.length());
                        int i;
                        for (i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String school_name = null,address = null, imgname = null, contact = null, sid=null;
                            String description = "Lorem ipsum dolor sit amet, proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
                            try {
                                school_name = jsonObject1.getString("school_name");
                                address = jsonObject1.getString("address");
                                imgname = jsonObject1.getString("image");
                                sid = jsonObject1.getString("sid");
                                contact = jsonObject1.getString("contact");
                                //description = jsonObject1.getString("description");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            schoolList.add(new Data(school_name, address, imgname,sid, contact));
                        }

                        Log.d("foodlist", "" + schoolList);
                        final SchoolAdapter schoolAdapter = new SchoolAdapter(school.this,schoolList);
                        recyclerView.setAdapter(schoolAdapter);
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error: ", "Volley needs attention");
            }

        });
    }

    //end of getUrls/////////////////////////////////////////////////////////////


    public void sendData(String school_name, String address, String imgname, String sid, String contact){

        RequestQueue requestQueue = Volley.newRequestQueue(school.this);
        getIp ip = new getIp();
        String del = ip.getIp();
        String URL = ""+del+":8080/order";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("school_name", ""+school_name);
            jsonObject.put("address", ""+address);
            jsonObject.put("imgname",imgname);
            jsonObject.put("sid",""+sid);
            jsonObject.put("contact",""+contact);

            Log.d("jsonobject", ""+jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();
        ConnectionManager.sendData(requestBody, requestQueue, URL, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.d("Data sent =", "" + result);
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error: ", "Volley needs attention");
            }

        });
    }
    /////////////////////////////////
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Filter.class);
        startActivity(intent);
    }
}