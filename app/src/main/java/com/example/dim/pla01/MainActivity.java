package com.example.dim.pla01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.dim.pla01.managers.DbSyncTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnRestService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RestRequestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnLocalService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocalBrowseActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnUtiliateurService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UtilisateurBrowseActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnVinService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VinBrowseActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnTypeVinService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TypeVinBrowseActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnVigneronService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VigneronBrowseActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnCaveService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CaveBrowseActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnSyncService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DbSyncTest testSync = new DbSyncTest("http://10.0.2.2:8080/caveavinA/webresources/com.mycompany.caveavina.cave/");
                testSync.syncExtToLocal();
                testSync.showAfterSync();
            }
        });

    }

}
