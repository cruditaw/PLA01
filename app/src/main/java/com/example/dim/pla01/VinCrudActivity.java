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

import com.example.dim.pla01.models.VinModel;

public class VinCrudActivity extends AppCompatActivity {

    private EditText editLibelle;
    private EditText editDomaine;
    private EditText editAnnee;
    private EditText editMiseBouteille;
    private EditText editType;
    private EditText editVigneron;
    private VinModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vin_crud);
        dbHelper = new VinModel(this);

        //lvEditVin = ((ListView) findViewById(R.id.))
        editLibelle = ((EditText)findViewById(R.id.itVinLibelle));
        editDomaine = ((EditText)findViewById(R.id.itVinDomaine));
        editAnnee = ((EditText)findViewById(R.id.itVinAnnee));
        editMiseBouteille = ((EditText)findViewById(R.id.itVinMB));
        editType = ((EditText)findViewById(R.id.itVinType));
        editVigneron = ((EditText)findViewById(R.id.itVinVigneron));

        Intent intent = getIntent();
        final Integer vinID = intent.getIntExtra(VinBrowseActivity.KEY_EXTRA_VIN_ID, 0);


        if (vinID > 0)
        {
            Cursor rs = dbHelper.getVin(vinID);
            rs.moveToFirst();
            String vinLibelle = rs.getString(rs.getColumnIndex(VinModel.VIN_COLUMN_LIBELLE));
            String vinDomaine = rs.getString(rs.getColumnIndex(VinModel.VIN_COLUMN_DOMAINE));
            String vinAnnee = String.valueOf(rs.getInt(rs.getColumnIndex(VinModel.VIN_COLUMN_ANNEE)));
            String vinMB = rs.getString(rs.getColumnIndex(VinModel.VIN_COLUMN_MISEBOUTEILLE));
            String vinType = rs.getString(rs.getColumnIndex(VinModel.VIN_COLUMN_TYPE));
            String vinVigneron = rs.getString(rs.getColumnIndex(VinModel.VIN_COLUMN_VIGNERON));


            editLibelle.setText(vinLibelle);
            editDomaine.setText(vinDomaine);
            editAnnee.setText(vinAnnee);
            editMiseBouteille.setText(vinMB);
            editType.setText(vinType);
            editVigneron.setText(vinVigneron);

            if (!rs.isClosed()) {
                rs.close();
            }
        }


        ((Button)findViewById(R.id.btnVinValidate)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                persistVin(vinID);
            }
        });
    }


    public void persistVin(Integer personID) {
        if(personID > 0) {
            if(dbHelper.updateVin(personID, editLibelle.getText().toString(),
                    editDomaine.getText().toString(),
                    editAnnee.getText().toString(),
                    editMiseBouteille.getText().toString(),
                    Integer.parseInt(editType.getText().toString()),
                    Integer.valueOf(editVigneron.getText().toString()))) {

                Toast.makeText(getApplicationContext(), "Vin Update Successful", Toast.LENGTH_SHORT).show();
                Intent intentUpdate = new Intent(getApplicationContext(), VinBrowseActivity.class);
                intentUpdate.putExtra("vinid", 0);
                intentUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentUpdate);
            }
            else {
                Toast.makeText(getApplicationContext(), "Vin Update Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(dbHelper.insertVin(editLibelle.getText().toString(),
                    editDomaine.getText().toString(),
                    editAnnee.getText().toString(),
                    editMiseBouteille.getText().toString(),
                    Integer.parseInt(editType.getText().toString()),
                    Integer.valueOf(editVigneron.getText().toString()))) {
                Toast.makeText(getApplicationContext(), "Vin Inserted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Could not Insert vin", Toast.LENGTH_SHORT).show();
            }
            Intent intentInsert = new Intent(getApplicationContext(), VinBrowseActivity.class);
            intentInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentInsert);
        }
    }
}
