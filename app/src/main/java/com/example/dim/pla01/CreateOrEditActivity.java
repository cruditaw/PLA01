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

public class CreateOrEditActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editGender;
    private EditText editAge;
    PersonDbModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit);


        dbHelper = new PersonDbModel(this);

        editName = ((EditText)findViewById(R.id.inputName));
        editGender = ((EditText)findViewById(R.id.inputGender));
        editAge = ((EditText)findViewById(R.id.inputAge));

        Intent intent = getIntent();
        final Integer personID = intent.getIntExtra(LocalBrowseActivity.KEY_EXTRA_CONTACT_ID, 0);
//        PersonEntity person = dbHelper.makeDummy(personID);

        if (personID > 0)
        {
            Cursor rs = dbHelper.getPerson(personID);
            rs.moveToFirst();
            String personName = rs.getString(rs.getColumnIndex(PersonDbModel.PERSON_COLUMN_NAME));
            String personGender = rs.getString(rs.getColumnIndex(PersonDbModel.PERSON_COLUMN_GENDER));
            String personAge = String.valueOf(rs.getInt(rs.getColumnIndex(PersonDbModel.PERSON_COLUMN_AGE)));

            editName.setText(personName);
            editGender.setText(personGender);
            editAge.setText(personAge);

            if (!rs.isClosed()) {
                rs.close();
            }
        }


        ((Button)findViewById(R.id.btnValidate)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                persistPerson(personID);

            }
        });

    }


    public void persistPerson(Integer personID) {
        if(personID > 0) {
            if(dbHelper.updatePerson(personID, editName.getText().toString(),
                    editGender.getText().toString(),
                    Integer.parseInt(editAge.getText().toString()))) {
                Toast.makeText(getApplicationContext(), "Person Update Successful", Toast.LENGTH_SHORT).show();
                Intent intentUpdate = new Intent(getApplicationContext(), MainActivity.class);
                intentUpdate.putExtra("cid", 0);
                intentUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentUpdate);
            }
            else {
                Toast.makeText(getApplicationContext(), "Person Update Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(dbHelper.insertPerson(editName.getText().toString(),
                    editGender.getText().toString(),
                    Integer.parseInt(editAge.getText().toString()))) {
                Toast.makeText(getApplicationContext(), "Person Inserted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Could not Insert person", Toast.LENGTH_SHORT).show();
            }
            Intent intentInsert = new Intent(getApplicationContext(), MainActivity.class);
            intentInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentInsert);
        }
    }


}
