package com.hashinclude.medex;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
                        "http://192.168.43.201/MedEx/public/api/register",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jO = new JSONObject(response);
                                    if (jO.has("info")){
                                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SignIn.this);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("token", jO.getString("token"));
                                        editor.putString("name", jO.getJSONArray("info").getJSONObject(0).getString("name"));
                                        editor.apply();
                                        Toast.makeText(SignIn.this, sp.getString("name","default")+" success", Toast.LENGTH_SHORT).show();
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
                                    if(new JSONObject(new String(error.networkResponse.data)).getString("error").contentEquals("invalid credentials")){
                                        Toast.makeText(SignIn.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    } else{
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
            }
        });
    }
}
