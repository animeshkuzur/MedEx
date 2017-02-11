package com.hashinclude.medex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Dosage extends AppCompatActivity {

    CheckBox checkbox_everyday, checkbox_monday, checkbox_tuesday, checkbox_wednesday;
    CheckBox checkbox_thursday, checkbox_friday, checkbox_saturday, checkbox_sunday;
    CheckBox checkbox_morning, checkbox_afternoon, checkbox_night;
    EditText input_weeks;
    Button dosage_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosage);
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
                        "http://192.168.43.201/MedEx/public/api/addschedule",
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
            }
        });
    }
}
