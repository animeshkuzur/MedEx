package com.hashinclude.medex;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hashinclude.medex.activities.MainActivity;
import com.hashinclude.medex.activities.SignIn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dosage extends AppCompatActivity {

    CheckBox checkbox_everyday, checkbox_monday, checkbox_tuesday, checkbox_wednesday;
    CheckBox checkbox_thursday, checkbox_friday, checkbox_saturday, checkbox_sunday;
    CheckBox checkbox_morning, checkbox_afternoon, checkbox_night;
    EditText input_weeks;
    Button dosage_submit;
    String token, time = "", day = "";
    String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosage);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Dosage.this);
        token = sp.getString("token","");

        checkbox_everyday = (CheckBox) findViewById(R.id.checkbox_everyday);
        checkbox_monday = (CheckBox) findViewById(R.id.checkbox_monday);
        checkbox_tuesday = (CheckBox) findViewById(R.id.checkbox_tuesday);
        checkbox_wednesday = (CheckBox) findViewById(R.id.checkbox_wednesday);
        checkbox_thursday = (CheckBox) findViewById(R.id.checkbox_thursday);
        checkbox_friday = (CheckBox) findViewById(R.id.checkbox_friday);
        checkbox_saturday = (CheckBox) findViewById(R.id.checkbox_saturday);
        checkbox_sunday = (CheckBox) findViewById(R.id.checkbox_sunday);
        checkbox_morning = (CheckBox) findViewById(R.id.checkbox_morning);
        checkbox_afternoon = (CheckBox) findViewById(R.id.checkbox_afternoon);
        checkbox_night = (CheckBox) findViewById(R.id.checkbox_night);
        input_weeks = (EditText) findViewById(R.id.input_weeks);
        dosage_submit = (Button) findViewById(R.id.dosage_submit);

        dosage_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        Constants.BASE_URL+"MedEx/laravel/public/api/addschedule",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject json = new JSONObject(response);
                                    info = json.getString("info");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Dosage.this, "Error receiving data!", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("token", token);
                        params.put("medicine_id", "");
                        params.put("dosage", "");
                        if (checkbox_morning.isChecked())
                            time += "0,";
                        if (checkbox_afternoon.isChecked())
                            time += "1,";
                        if (checkbox_night.isChecked())
                            time += "2,";

                        if (checkbox_everyday.isChecked())
                            day += "0,";
                        if (checkbox_monday.isChecked())
                            day += "1,";
                        if (checkbox_tuesday.isChecked())
                            day += "2,";
                        if (checkbox_wednesday.isChecked())
                            day += "3,";
                        if (checkbox_thursday.isChecked())
                            day += "4,";
                        if (checkbox_friday.isChecked())
                            day += "5,";
                        if (checkbox_saturday.isChecked())
                            day += "6,";
                        if (checkbox_sunday.isChecked())
                            day += "7,";

                        params.put("time", time);
                        params.put("day", day);

                        return params;
                    }
                };
                MySingleton.getInstance().addToRequestQueue(stringRequest);
            }
        });
    }
}
