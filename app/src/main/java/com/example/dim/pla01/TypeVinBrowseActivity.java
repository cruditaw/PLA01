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

import com.example.dim.pla01.entities.TypeVinEntity;
import com.example.dim.pla01.models.TypeVinModel;

import java.util.ArrayList;
import java.util.List;

public class TypeVinBrowseActivity extends AppCompatActivity {
    public final static String KEY_EXTRA_TYPEVIN_ID = "KEY_EXTRA_TYPEVIN_ID";

    private int currentID;
    private ListView typeVinListView;
    private List<TypeVinEntity> typeVinList;
    private TypeVinModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_vin_browse);


        dbHelper = new TypeVinModel(this);
        final Cursor cursor = dbHelper.getAllTypeVins();

        typeVinList = makeTypeVinsList(cursor);

        currentID = 0;  // list current selection index
        typeVinListView = (ListView) findViewById(R.id.typeVinListView); // list_view binding


        // list_view columns
        String[] columns = new String[]{
                TypeVinModel.TYPEVIN_COLUMN_ID,
                TypeVinModel.TYPEVIN_COLUMN_LIBELLE
        };

        // cave_item.xml layout widget name for list_view
        int[] widgets = new int[]{
                R.id.typevinID,
                R.id.typevinLibelle
        };

        // setting parameters to list_view
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.typevin_item, cursor, columns, widgets, 0);
        typeVinListView.setAdapter(cursorAdapter);

        // intent check
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            currentID = receivedIntent.getIntExtra("tvid", 0);
            System.out.println("-- TypevinBrowseActivity -- receivedIntent -> " + currentID);
        }

        // new_button on click listener
        ((Button) findViewById(R.id.btnTypeVinNew)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeVinBrowseActivity.this, TypeVinCrudActivity.class);
                intent.putExtra(KEY_EXTRA_TYPEVIN_ID, 0);
                startActivity(intent);
            }
        });

        // modify_button on click listener
        ((Button) findViewById(R.id.btnTypeVinMod)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentID > 0) {
                    Intent intent = new Intent(getApplicationContext(), TypeVinCrudActivity.class);
                    intent.putExtra(KEY_EXTRA_TYPEVIN_ID, currentID);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Aucnune selection !", Toast.LENGTH_LONG).show();
                }
            }
        });

        // delete_button on click listener -> Dialog builder should be moved elsewhere
        ((Button) findViewById(R.id.btnTypeVinDel)).setOnClickListener(new OnClickListener() {
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
        typeVinListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) typeVinListView.getItemAtPosition(position);
                currentID = itemCursor.getInt(itemCursor.getColumnIndex(TypeVinModel.TYPEVIN_COLUMN_ID));
                System.out.println("-- typeVinListView -- OnItemClickListener -> CURRENT ID : " + currentID);
            }
        });

    }

    @NonNull
    private Builder buildDeleteDialog() {
        Builder builder = new Builder(TypeVinBrowseActivity.this);
        builder.setMessage(R.string.labelDeleteDialog)
                .setPositiveButton(R.string.labelYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.deleteTypeVin(currentID);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TypeVinBrowseActivity.class);
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

    private ArrayList<TypeVinEntity> makeTypeVinsList(Cursor cursor) {
        ArrayList<TypeVinEntity> list = new ArrayList<>();
        TypeVinEntity pe;

        cursor.moveToFirst();
        cursor.moveToPrevious();

        while (cursor.moveToNext()) {
            pe = new TypeVinEntity();
            pe.setTvinId(cursor.getInt(cursor.getColumnIndex(TypeVinModel.TYPEVIN_COLUMN_ID)));
            pe.setTvinLibelle(cursor.getString(cursor.getColumnIndex(TypeVinModel.TYPEVIN_COLUMN_LIBELLE)));

            list.add(pe);
        }
        return list;
    }
}
