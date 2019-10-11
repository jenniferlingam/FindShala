package com.example.findshaala;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

    @SuppressLint("Registered")
    public class login extends AppCompatActivity {
        Button btnLogin, btnRegister;
        EditText username,password;
        SessionManager sessionManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);

            btnLogin = findViewById(R.id.login);
            btnRegister = findViewById(R.id.register1);
            username=findViewById(R.id.username);
            password=findViewById(R.id.password);

            sessionManager = new SessionManager(getApplicationContext());
            if (sessionManager.isLoggin()){
                Intent intent = new Intent(getApplicationContext(),registration.class);
                startActivity(intent);
            }

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Username=username.getText().toString().trim();
                    String Password=password.getText().toString().trim();

                    if (!Username.isEmpty() || !Password.isEmpty()) {
                        Login(Username,Password);
                        //shared pref
                        username.setText("");
                        password.setText("");
                    }
                    else{
                        username.setError("Please enter a valid username");
                        password.setError("Please enter a valid password");
                    }
                }
            });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(),registration.class);
                    startActivity(intent);
                }
            });

        }

        private void Login(final String username, final String password) {
            getIp ip = new getIp();
            String del = ip.getIp();
            RequestQueue requestQueue = Volley.newRequestQueue(login.this);
            String URL = ""+del+":8080/login";

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                jsonObject.put("password", password);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            final String requestBody = jsonObject.toString();
            Log.d("str","str is"+requestBody);

            ConnectionManager.sendData(requestBody, requestQueue, URL, new ConnectionManager.VolleyCallback(){
                @Override
                public void onSuccessResponse(String result) {

                    Log.d("RESULT","RESULTS "+result);
                    if (result.equals("1")) {

                        Toast.makeText(login.this, "Hello "+username, Toast.LENGTH_SHORT).show();

                        sessionManager.createSession(username);
                        System.out.println("Email"+username);

                        Intent intent = new Intent(getApplicationContext(),Filter.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(login.this, "Ooops! Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast toast = Toast.makeText(login.this,"Volley needs attention" + error,Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }}