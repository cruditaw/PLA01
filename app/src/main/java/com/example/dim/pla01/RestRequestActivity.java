package com.example.dim.pla01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RestRequestActivity extends AppCompatActivity {

    String url = "http://10.0.2.2:8080/caveavinA/webresources/com.mycompany.caveavina.cave/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_request);


            // todo : call correct uri depending on application logic !

        ((Button)findViewById(R.id.btnRqstExec)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                RestRequest requestHelper = new RestRequest(url);
                requestHelper.runRequest();
            }
        });


    }
}
