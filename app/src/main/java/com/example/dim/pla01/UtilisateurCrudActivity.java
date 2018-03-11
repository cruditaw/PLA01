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

import com.example.dim.pla01.models.UtilisateurModel;

public class UtilisateurCrudActivity extends AppCompatActivity {

    private EditText editNom;
    private EditText editPrenom;
    private EditText editMail;
    private UtilisateurModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur_crud);

        dbHelper = new UtilisateurModel(this);

        //lvEditVin = ((ListView) findViewById(R.id.))
        editNom = ((EditText)findViewById(R.id.itUtilisateurNom));
        editPrenom = ((EditText)findViewById(R.id.itUtilisateurPrenom));
        editMail = ((EditText)findViewById(R.id.itUtilisateurMail));

        Intent intent = getIntent();

        final Integer utilisateurID = intent.getIntExtra(UtilisateurBrowseActivity.KEY_EXTRA_UTILISATEUR_ID, 0);

        if (utilisateurID > 0)
        {
            Cursor rs = dbHelper.getUtilisateur(utilisateurID);
            rs.moveToFirst();
            String uNom = rs.getString(rs.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_NOM));
            String uPrenom = rs.getString(rs.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_PRENOM));
            String uMail = String.valueOf(rs.getInt(rs.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_MAIL)));

            editNom.setText(uNom);
            editPrenom.setText(uPrenom);
            editMail.setText(uMail);

            if (!rs.isClosed()) {
                rs.close();
            }
        }

        ((Button)findViewById(R.id.btnUtilisateurValidate)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                persistUtilisateur(utilisateurID);
            }
        });
    }

    public void persistUtilisateur(Integer personID) {
        if(personID > 0) {
            if(dbHelper.updateUtilisateur(personID, editNom.getText().toString(),
                    editPrenom.getText().toString(),
                    editMail.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Utilisateur Update Successful", Toast.LENGTH_SHORT).show();
                Intent intentUpdate = new Intent(getApplicationContext(), UtilisateurBrowseActivity.class);
                intentUpdate.putExtra("uid", 0);
                intentUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentUpdate);
            }
            else {
                Toast.makeText(getApplicationContext(), "Utilisateur Update Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(dbHelper.insertUtilisateur(editNom.getText().toString(),
                    editPrenom.getText().toString(),
                    editMail.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Utilisateur Inserted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Could not Insert Utilisateur", Toast.LENGTH_SHORT).show();
            }
            Intent intentInsert = new Intent(getApplicationContext(), UtilisateurBrowseActivity.class);
            intentInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentInsert);
        }
    }

}
