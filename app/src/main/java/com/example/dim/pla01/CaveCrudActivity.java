package com.example.dim.pla01;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dim.pla01.models.CaveModel;

/**
 * Created by dim on 15/02/2018.
 */

public class CaveCrudActivity extends AppCompatActivity {

    private EditText editVin;
    private EditText editUtilisateur;
    private EditText editNombre;
    private CaveModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cave_crud);


        dbHelper = new CaveModel(this);

        editVin= ((EditText)findViewById(R.id.itCaveVin));
        editUtilisateur = ((EditText)findViewById(R.id.itCaveUtilisateur));
        editNombre = ((EditText)findViewById(R.id.itCaveNombre));

        Intent intent = getIntent();
        final Integer caveID = intent.getIntExtra(CaveBrowseActivity.KEY_EXTRA_CAVE_ID, 0);


        if (caveID > 0)
        {
            Cursor rs = dbHelper.getCave(caveID);
            rs.moveToFirst();
            String caveVin = rs.getString(rs.getColumnIndex(CaveModel.CAVE_COLUMN_VIN));
            String caveUtilisateur = rs.getString(rs.getColumnIndex(CaveModel.CAVE_COLUMN_UTILISATEUR));
            String caveNombre = String.valueOf(rs.getInt(rs.getColumnIndex(CaveModel.CAVE_COLUMN_NBOUTEILLES)));

            editVin.setText(caveVin);
            editUtilisateur.setText(caveUtilisateur);
            editNombre.setText(caveNombre);

            if (!rs.isClosed()) {
                rs.close();
            }
        }


        ((Button)findViewById(R.id.btnCaveValidate)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                persistCave(caveID);

            }
        });

    }

    public void persistCave(Integer personID) {
        if(personID > 0) {
            if(dbHelper.updateCave(personID, Integer.parseInt(editVin.getText().toString()),
                    Integer.parseInt(editUtilisateur.getText().toString()),
                    Integer.parseInt(editNombre.getText().toString()))) {
                Toast.makeText(getApplicationContext(), "Cave Update Successful", Toast.LENGTH_SHORT).show();
                Intent intentUpdate = new Intent(getApplicationContext(), CaveBrowseActivity.class);
                intentUpdate.putExtra("cid", 0);
                intentUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentUpdate);
            }
            else {
                Toast.makeText(getApplicationContext(), "Cave Update Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(dbHelper.insertCave(Integer.parseInt(editVin.getText().toString()),
                    Integer.parseInt(editUtilisateur.getText().toString()),
                    Integer.parseInt(editNombre.getText().toString()))) {
                Toast.makeText(getApplicationContext(), "Cave Inserted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Could not Insert cave", Toast.LENGTH_SHORT).show();
            }
            Intent intentInsert = new Intent(getApplicationContext(), CaveBrowseActivity.class);
            intentInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentInsert);
        }
    }


}
