package com.hashinclude.medex.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    EditText input_email, input_password;
    Button sign_in_done, sign_in_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SignIn.this);
        if (sp.contains("token")){
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        sign_in_done = (Button) findViewById(R.id.sign_in_done);
        sign_in_register = (Button) findViewById(R.id.sign_in_register);

        sign_in_done.setOnClickListener(this);
        sign_in_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_in_done:
                StringRequest myReq = new StringRequest(Request.Method.POST,
                        Constants.BASE_URL+"MedEx/laravel/public/api/login",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jO = new JSONObject(response);
                                    if (jO.has("token")){
                                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SignIn.this);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("token", jO.getString("token"));
                                        editor.putString("name", jO.getJSONArray("info").getJSONObject(0).getString("name"));
                                        editor.apply();
                                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
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
                                        Toast.makeText(SignIn.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(new JSONObject(new String(error.networkResponse.data)).has("error")){
                                        if(new JSONObject(new String(error.networkResponse.data)).getString("error").contentEquals("invalid credentials")){
                                            Toast.makeText(SignIn.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(SignIn.this, "Something went wrong", Toast.LENGTH_SHORT).show();}
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }) {
                    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", input_email.getText().toString());
                        params.put("password", input_password.getText().toString());

                        return params;
                    }
                };
                MySingleton.getInstance().addToRequestQueue(myReq);
                break;
            case R.id.sign_in_register:
                Intent intent = new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}


/*
StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BASE_URL_DEFAULT + "ImageName?UserId=" + uid,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    image_name[0] = json.getString("ImageName");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "Error receiving data!", Toast.LENGTH_SHORT).show();
    }
});
MySingleton.getInstance().addToRequestQueue(stringRequest);
 */