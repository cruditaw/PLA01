package com.example.dim.pla01;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
* FAIRE BACKING OBJECT
*
* */

public class MainActivity extends AppCompatActivity {

    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";


    private int currentID;
    private PersonEntity currentPerson;
    private ListView personListView;
    private List<PersonEntity> pList;
    PersonDbModel dbHelper;
    RestRequest requestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestHelper = new RestRequest();
        requestHelper.runRequest();
        dbHelper = new PersonDbModel(this); // model init
        final Cursor cursor = dbHelper.getAllPersons(); // persons list as cursor

        pList = makePersonsList(cursor);

        for (PersonEntity pe : pList) {
            System.out.println("----- Retreived PersonEntity : ");
            System.out.println(pe.toString());
            System.out.println("\n");
        }

        currentID = 0;  // list current selection index
        personListView = (ListView)findViewById(R.id.personListView); // list_view binding



        // list_view columns
        String [] columns = new String[] {
                PersonDbModel.PERSON_COLUMN_ID,
                PersonDbModel.PERSON_COLUMN_NAME,
                PersonDbModel.PERSON_COLUMN_GENDER,
                PersonDbModel.PERSON_COLUMN_AGE
        };

        // person_item.xml layout widget name for list_view
        int [] widgets = new int[] {
                R.id.personID,
                R.id.personName,
                R.id.personGender,
                R.id.personAge
        };

        // setting parameters to list_view
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.person_item, cursor, columns, widgets, 0);
        personListView.setAdapter(cursorAdapter);



        // intent check
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            currentID = receivedIntent.getIntExtra("cid", 0);
            System.out.println("---------------- MainActivity INIT -> "+currentID);
        }

        // new_button on click listener
        ((Button) findViewById(R.id.btnAddNew)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateOrEditActivity.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, 0);
                startActivity(intent);
            }
        });

        // modify_button on click listener
        ((Button) findViewById(R.id.btnModify)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentID > 0) {
                    Intent intent = new Intent(getApplicationContext(), CreateOrEditActivity.class);
                    intent.putExtra(KEY_EXTRA_CONTACT_ID, currentID);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Aucnune selection !", Toast.LENGTH_LONG).show();
                }
            }
        });

        // delete_button on click listener -> Dialog builder should be moved elsewhere
        ((Button)findViewById(R.id.btnDelete)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentID > 0) {

                    Builder builder = buildDeleteDialog(); // defining dialog build
                    AlertDialog d = builder.create(); // creating dialog
                    d.setTitle(R.string.labelDeleteDialogTitle);
                    d.show();
                    return;

                } else {
                    Toast.makeText(getApplicationContext(), "Aucune selection !", Toast.LENGTH_LONG).show();
                }
            }
        });

        // list_view on item click listener
        personListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) personListView.getItemAtPosition(position);
                currentID = itemCursor.getInt(itemCursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_ID));
                System.out.println("-- personListView -- OnItemClickListener -> CURRENT ID : "+currentID);

            }
        });

    }

    @NonNull
    private Builder buildDeleteDialog() {
        Builder builder = new Builder(MainActivity.this);
        builder.setMessage(R.string.labelDeleteDialog)
                .setPositiveButton(R.string.labelYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.deletePerson(currentID);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })

                .setNegativeButton(R.string.labelNo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        // nothing to do.. ?
                    }
                });
        return builder;
    }

    private ArrayList<PersonEntity> makePersonsList(Cursor cursor) {
        ArrayList<PersonEntity> list = new ArrayList<>();
        PersonEntity pe;

        cursor.moveToFirst();
        cursor.moveToPrevious();

        while (cursor.moveToNext()) {
            pe = new PersonEntity();
            pe.set_id(cursor.getInt(cursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_ID)));
            pe.setName(cursor.getString(cursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_NAME)));
            pe.setGenre(cursor.getString(cursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_GENDER)));
            pe.setAge(cursor.getInt(cursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_AGE)));

            list.add(pe);
        }
        return list;
    }


}
