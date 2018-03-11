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

import com.example.dim.pla01.models.VigneronModel;

public class VigneronCrudActivity extends AppCompatActivity {

    private EditText editLibelle;
    private EditText editAddr;
    private EditText editAddr1;
    private EditText editAddr2;
    private EditText editVille;
    private EditText editCp;
    private EditText editTel;
    private EditText editMail;
    private VigneronModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigneron_crud);

        dbHelper = new VigneronModel(this);

        //lvEditVin = ((ListView) findViewById(R.id.))
        editLibelle= ((EditText)findViewById(R.id.itVigneronLibelle));
        editAddr = ((EditText)findViewById(R.id.itVigneronAdresse));
        editAddr1 = ((EditText)findViewById(R.id.itVigneronAdresse1));
        editAddr2 = ((EditText)findViewById(R.id.itVigneronAdresse2));
        editVille = ((EditText)findViewById(R.id.itVigneronVille));
        editCp = ((EditText)findViewById(R.id.itVigneronCP));
        editTel = ((EditText)findViewById(R.id.itVigneronTel));
        editMail = ((EditText)findViewById(R.id.itVigneronMail));

        Intent intent = getIntent();
        final Integer vigneronID = intent.getIntExtra(VigneronBrowseActivity.KEY_EXTRA_VIGNERON_ID, 0);

        if (vigneronID > 0)
        {
            Cursor rs = dbHelper.getVigneron(vigneronID);
            rs.moveToFirst();
            String vignLibelle = rs.getString(rs.getColumnIndex(VigneronModel.VIGNERON_COLUMN_LIBELLE));
            String vignAddr = rs.getString(rs.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ADRESSE));
            String vignAddr1 = String.valueOf(rs.getInt(rs.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ADRESSE1)));
            String vignAddr2 = String.valueOf(rs.getInt(rs.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ADRESSE2)));
            String vignVille = String.valueOf(rs.getInt(rs.getColumnIndex(VigneronModel.VIGNERON_COLUMN_VILLE)));
            String vignCp = String.valueOf(rs.getInt(rs.getColumnIndex(VigneronModel.VIGNERON_COLUMN_CP)));
            String vignTel = String.valueOf(rs.getInt(rs.getColumnIndex(VigneronModel.VIGNERON_COLUMN_TEL)));
            String vignMail = String.valueOf(rs.getInt(rs.getColumnIndex(VigneronModel.VIGNERON_COLUMN_MAIL)));

            editLibelle.setText(vignLibelle);
            editAddr.setText(vignAddr);
            editAddr1.setText(vignAddr1);
            editAddr2.setText(vignAddr2);
            editVille.setText(vignVille);
            editCp.setText(vignCp);
            editTel.setText(vignTel);
            editMail.setText(vignMail);

            if (!rs.isClosed()) {
                rs.close();
            }
        }

        ((Button)findViewById(R.id.btnVigneronValidate)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                persistVigneron(vigneronID);
            }
        });
    }

    public void persistVigneron(Integer personID) {
        if(personID > 0) {
            if(dbHelper.updateVigneron(personID, editLibelle.getText().toString(),
                    editAddr.getText().toString(),
                    editAddr1.getText().toString(),
                    editAddr2.getText().toString(),
                    editVille.getText().toString(),
                    editCp.getText().toString(),
                    editTel.getText().toString(),
                    editMail.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Vigneron Update Successful", Toast.LENGTH_SHORT).show();
                Intent intentUpdate = new Intent(getApplicationContext(), VigneronBrowseActivity.class);
                intentUpdate.putExtra("vid", 0);
                intentUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentUpdate);
            }
            else {
                Toast.makeText(getApplicationContext(), "Vigneron Update Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(dbHelper.insertVigneron(editLibelle.getText().toString(),
                    editAddr.getText().toString(),
                    editAddr1.getText().toString(),
                    editAddr2.getText().toString(),
                    editVille.getText().toString(),
                    editCp.getText().toString(),
                    editTel.getText().toString(),
                    editMail.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Vigneron Inserted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Could not Insert Vigneron", Toast.LENGTH_SHORT).show();
            }
            Intent intentInsert = new Intent(getApplicationContext(), VigneronBrowseActivity.class);
            intentInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentInsert);
        }
    }
}
