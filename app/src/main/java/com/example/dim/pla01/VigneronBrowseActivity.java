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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.dim.pla01.entities.VigneronEntity;
import com.example.dim.pla01.models.VigneronModel;

import java.util.ArrayList;
import java.util.List;

public class VigneronBrowseActivity extends AppCompatActivity {


    public final static String KEY_EXTRA_VIGNERON_ID = "KEY_EXTRA_VIGNERON_ID";

    private int currentID;
    private ListView vigneronListView;
    private List<VigneronEntity> vigneronList;
    private VigneronModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigneron_browse);

        dbHelper = new VigneronModel(this);
        final Cursor cursor = dbHelper.getAllVignerons();

        vigneronList = makeVigneronsList(cursor);

        currentID = 0;  // list current selection index
        vigneronListView = (ListView) findViewById(R.id.vigneronListView); // list_view binding

        // list_view columns
        String[] columns = new String[]{
                VigneronModel.VIGNERON_COLUMN_ID,
                VigneronModel.VIGNERON_COLUMN_LIBELLE,
                VigneronModel.VIGNERON_COLUMN_ADRESSE,
                VigneronModel.VIGNERON_COLUMN_ADRESSE1,
                VigneronModel.VIGNERON_COLUMN_ADRESSE2,
                VigneronModel.VIGNERON_COLUMN_VILLE,
                VigneronModel.VIGNERON_COLUMN_CP,
                VigneronModel.VIGNERON_COLUMN_TEL,
                VigneronModel.VIGNERON_COLUMN_MAIL,
        };

        // cave_item.xml layout widget name for list_view
        int[] widgets = new int[]{
                R.id.vigneronID,
                R.id.vigneronLabel,
                R.id.vigneronAddr,
                R.id.vigneronAddr1,
                R.id.vigneronAddr2,
                R.id.vigneronVille,
                R.id.vigneronCP,
                R.id.vigneronTel,
                R.id.vigneronMail
        };

        // setting parameters to list_view
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.vigneron_item, cursor, columns, widgets, 0);
        vigneronListView.setAdapter(cursorAdapter);

        // intent check
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            currentID = receivedIntent.getIntExtra("vid", 0);
            System.out.println("-- VigneronBrowseActivity -- receivedIntent -> " + currentID);
        }

        // new_button on click listener
        ((Button) findViewById(R.id.btnVigneronNew)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VigneronBrowseActivity.this, VigneronCrudActivity.class);
                intent.putExtra(KEY_EXTRA_VIGNERON_ID, 0);
                startActivity(intent);
            }
        });

        // modify_button on click listener
        ((Button) findViewById(R.id.btnVigneronMod)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentID > 0) {
                    Intent intent = new Intent(getApplicationContext(), VigneronCrudActivity.class);
                    intent.putExtra(KEY_EXTRA_VIGNERON_ID, currentID);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Aucnune selection !", Toast.LENGTH_LONG).show();
                }
            }
        });

        // list_view on item click listener
        vigneronListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) vigneronListView.getItemAtPosition(position);
                currentID = itemCursor.getInt(itemCursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ID));
                System.out.println("-- vigneronListView -- OnItemClickListener -> CURRENT ID : " + currentID);

            }
        });
    }

    @NonNull
    private AlertDialog.Builder buildDeleteDialog() {
        Builder builder = new Builder(VigneronBrowseActivity.this);
        builder.setMessage(R.string.labelDeleteDialog)
                .setPositiveButton(R.string.labelYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.deleteVigneron(currentID);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CaveBrowseActivity.class);
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


    private ArrayList<VigneronEntity> makeVigneronsList(Cursor cursor) {
        ArrayList<VigneronEntity> list = new ArrayList<>();
        VigneronEntity pe;

        cursor.moveToFirst();
        cursor.moveToPrevious();

        while (cursor.moveToNext()) {
            pe = new VigneronEntity();
            pe.setVignId(cursor.getInt(cursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ID)));
            pe.setVignLibelle(cursor.getString(cursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_LIBELLE)));
            pe.setVignAdresse(cursor.getString(cursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ADRESSE)));
            pe.setVignAdresse1(cursor.getString(cursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ADRESSE1)));
            pe.setVignAdresse2(cursor.getString(cursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ADRESSE2)));
            pe.setVignVille(cursor.getString(cursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_VILLE)));
            pe.setVignCp(cursor.getString(cursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_CP)));
            pe.setVignTel(cursor.getString(cursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_TEL)));
            pe.setVignMail(cursor.getString(cursor.getColumnIndex(VigneronModel.VIGNERON_COLUMN_MAIL)));

            list.add(pe);
        }
        return list;
    }
}
