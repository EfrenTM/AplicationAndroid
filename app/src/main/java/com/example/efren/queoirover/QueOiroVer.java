package com.example.efren.queoirover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class QueOiroVer extends AppCompatActivity {

    Button buttonSearch;
    TextView textGender;
    Spinner OptionGenders;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_oiro_ver);



        textGender = findViewById(R.id.textGender);
        OptionGenders =  findViewById(R.id.SelectGender);
        buttonSearch = findViewById(R.id.buttonSearch);


        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                requestMessage();
            }
        });
    }

    private void validate() {
        int position = OptionGenders.getSelectedItemPosition();

        if(position == 0){
            Toast.makeText(this, "Select a correct option ", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestMessage(){
        url ="http://192.168.201.59:40000/api/list_gender";

        int position = OptionGenders.getSelectedItemPosition();

        if (position == 1){
             url += "/Shonen";
        }
        if(position == 2){
            url += "/Isekai";
        }
        if(position == 3){
            url += "/Harem";
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            textGender.setText(response.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(url.equals("http://192.168.201.59:40000/api/list_gender")){
                            textGender.setText("");
                        }else{
                            textGender.setText("Error: " + error.toString());
                        }

                    }
                });
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
