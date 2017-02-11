package com.hashinclude.medex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hashinclude.medex.Constants;
import com.hashinclude.medex.MySingleton;
import com.hashinclude.medex.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText input_email, input_password, input_phone, input_name, input_sex, input_age;
    Button sign_up_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        input_phone = (EditText) findViewById(R.id.input_phone);
        input_name = (EditText) findViewById(R.id.input_name);
        input_sex = (EditText) findViewById(R.id.input_sex);
        input_age = (EditText) findViewById(R.id.input_age);
        sign_up_done = (Button) findViewById(R.id.sign_up_done);

        sign_up_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest myReq = new StringRequest(Request.Method.POST,
                        Constants.BASE_URL+"MedEx/laravel/public/api/register",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jO = new JSONObject(response);
                                    if (jO.has("info")){
                                        if(jO.getString("info").contentEquals("user_registered")){

                                            Toast.makeText(SignUp.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignUp.this, SignIn.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }
                                } catch (JSONException ignored) {
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.i("k", new String(error.networkResponse.data));
                                try {
                                    if(error.networkResponse == null || error.networkResponse.data == null){
                                        Toast.makeText(SignUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(new JSONObject(new String(error.networkResponse.data)).has("error")){
                                        if(new JSONObject(new String(error.networkResponse.data)).getString("error").contentEquals("credentials_exists")){
                                            Toast.makeText(SignUp.this, "Credentials Exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                     else{
                                        Toast.makeText(SignUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();}
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }) {
                    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", input_email.getText().toString());
                        params.put("password", input_password.getText().toString());
                        params.put("phone", input_phone.getText().toString());
                        params.put("name", input_name.getText().toString());
                        params.put("sex", input_sex.getText().toString());
                        params.put("age", input_age.getText().toString());

                        return params;
                    }
                };
                MySingleton.getInstance().addToRequestQueue(myReq);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(SignUp.this,SignIn.class);
        startActivity(i);
        finish();
    }
}
