package com.example.dim.pla01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RestRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_request);


            // todo : call correct uri depending on application logic !
            String url = "http://10.0.2.2:8080/caveavinA/webresources/com.mycompany.caveavina.entities.vigneron/duplicate";

            RestRequest requestHelper = new RestRequest(url);
            requestHelper.runRequest();

    }
}
