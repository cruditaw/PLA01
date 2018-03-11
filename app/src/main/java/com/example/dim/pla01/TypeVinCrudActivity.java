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

import com.example.dim.pla01.models.TypeVinModel;

public class TypeVinCrudActivity extends AppCompatActivity {

    private EditText editLabel;
    private TypeVinModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_vin_crud);


        dbHelper = new TypeVinModel(this);

        //lvEditVin = ((ListView) findViewById(R.id.))
        editLabel = ((EditText)findViewById(R.id.itTypeVinLibelle));

        Intent intent = getIntent();
        final Integer typevinID = intent.getIntExtra(TypeVinBrowseActivity.KEY_EXTRA_TYPEVIN_ID, 0);


        if (typevinID > 0)
        {
            Cursor rs = dbHelper.getTypeVin(typevinID);
            rs.moveToFirst();
            String typevinLibelle = rs.getString(rs.getColumnIndex(TypeVinModel.TYPEVIN_COLUMN_LIBELLE));

            editLabel.setText(typevinLibelle);

            if (!rs.isClosed()) {
                rs.close();
            }
        }


        ((Button)findViewById(R.id.btnCaveValidate)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                persistTypeVin(typevinID);
            }
        });
    }

    public void persistTypeVin(Integer personID) {
        if(personID > 0) {
            if(dbHelper.updateTypeVin(personID, editLabel.getText().toString())) {
                Toast.makeText(getApplicationContext(), "TypeVin Update Successful", Toast.LENGTH_SHORT).show();
                Intent intentUpdate = new Intent(getApplicationContext(), TypeVinBrowseActivity.class);
                intentUpdate.putExtra("tvid", 0);
                intentUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentUpdate);
            }
            else {
                Toast.makeText(getApplicationContext(), "TypeVin Update Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(dbHelper.insertTypeVin(editLabel.getText().toString())) {
                Toast.makeText(getApplicationContext(), "TypeVin Inserted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Could not Insert TypeVin", Toast.LENGTH_SHORT).show();
            }
            Intent intentInsert = new Intent(getApplicationContext(), TypeVinBrowseActivity.class);
            intentInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentInsert);
        }
    }
}
