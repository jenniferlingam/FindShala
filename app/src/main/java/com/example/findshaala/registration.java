package com.example.findshaala;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class registration extends AppCompatActivity {
    private Button register1;
    private EditText firstname,lastname,email,password,username,confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        firstname=findViewById(R.id.fname);
        lastname=findViewById(R.id.lname);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);
        register1=findViewById(R.id.register2);

        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Firstname =firstname.getText().toString().trim();
                String Lastname =lastname.getText().toString().trim();
                String Username =username.getText().toString().trim();
                String Password=password.getText().toString().trim();
                String Email =email.getText().toString().trim();
                String Confirm_Password =confirmpassword.getText().toString().trim();

                if (!Firstname.isEmpty() || !Lastname.isEmpty() || !Username.isEmpty() || !Password.isEmpty() || !Email.isEmpty() || !Confirm_Password.isEmpty()) {
                        Register(Firstname, Lastname, Username, Email, Password, Confirm_Password);
                }
                else{
                    firstname.setError("enter your firstname");
                    lastname.setError("enter your lastname");
                    username.setError("enter your username");
                    email.setError("enter your email id");
                    password.setError("enter your Password");
                    confirmpassword.setError("password not matching");
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
        finish();
        return true;
    }

    private void Register(String Firstname,String Lastname, String Username,String Email, String Password, String Confirm_Password) {
        getIp ip = new getIp();
        String del = ip.getIp();
        RequestQueue requestQueue = Volley.newRequestQueue(registration.this);
        String URL = ""+del+":8080/register";


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", Firstname);
            jsonObject.put("lastname", Lastname);
            jsonObject.put("username", Username);
            jsonObject.put("email", Email);
            jsonObject.put("password", Password);
            jsonObject.put("confirmpassword",Confirm_Password);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();
        Log.d("str_register","strREG is"+requestBody);

        ConnectionManager.sendData(requestBody, requestQueue, URL, new ConnectionManager.VolleyCallback(){
            @Override
            public void onSuccessResponse(String result) {
                Log.d("RESULT","RESULTS "+result);
                if (result.equals("1")) {
                    Toast.makeText(registration.this, "Hello", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),Filter.class);
                    startActivity(in);

                } else {
                    Toast.makeText(registration.this, "Oops! Try Again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(registration.this,"Volley needs attention" + error,Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}