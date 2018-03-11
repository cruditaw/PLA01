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

import com.example.dim.pla01.entities.UtilisateurEntity;
import com.example.dim.pla01.models.UtilisateurModel;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurBrowseActivity extends AppCompatActivity {

    public final static String KEY_EXTRA_UTILISATEUR_ID = "KEY_EXTRA_UTILISATEUR_ID";

    private int currentID;
    private ListView utilisateurListView;
    private List<UtilisateurEntity> utilisateurList;
    private UtilisateurModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur_browse);

        dbHelper = new UtilisateurModel(this);
        final Cursor cursor = dbHelper.getAllUtilisateurs();

        utilisateurList = makeUtilisateursList(cursor);

        currentID = 0;  // list current selection index
        utilisateurListView = (ListView) findViewById(R.id.utilisateurListView); // list_view binding


        // list_view columns
        String[] columns = new String[]{
                UtilisateurModel.UTILISATEUR_COLUMN_ID,
                UtilisateurModel.UTILISATEUR_COLUMN_NOM,
                UtilisateurModel.UTILISATEUR_COLUMN_PRENOM,
                UtilisateurModel.UTILISATEUR_COLUMN_MAIL
        };

        // cave_item.xml layout widget name for list_view
        int[] widgets = new int[]{
                R.id.utilisateurID,
                R.id.utilisateurNom,
                R.id.utilisateurPrenom,
                R.id.utilisateurMail
        };

        // setting parameters to list_view
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.utilisateur_item, cursor, columns, widgets, 0);
        utilisateurListView.setAdapter(cursorAdapter);

        // intent check
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            currentID = receivedIntent.getIntExtra("uid", 0);
            System.out.println("-- UtilisateurBrowseActivity -- receivedIntent -> " + currentID);
        }

        // new_button on click listener
        ((Button) findViewById(R.id.btnUtilisateurNew)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UtilisateurBrowseActivity.this, UtilisateurCrudActivity.class);
                intent.putExtra(KEY_EXTRA_UTILISATEUR_ID, 0);
                startActivity(intent);
            }
        });

        // modify_button on click listener
        ((Button) findViewById(R.id.btnUtilisateurMod)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentID > 0) {
                    Intent intent = new Intent(getApplicationContext(), UtilisateurCrudActivity.class);
                    intent.putExtra(KEY_EXTRA_UTILISATEUR_ID, currentID);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Aucnune selection !", Toast.LENGTH_LONG).show();
                }
            }
        });

        // delete_button on click listener -> Dialog builder should be moved elsewhere
        ((Button) findViewById(R.id.btnUtilisateurDel)).setOnClickListener(new OnClickListener() {
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
        utilisateurListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) utilisateurListView.getItemAtPosition(position);
                currentID = itemCursor.getInt(itemCursor.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_ID));
                System.out.println("-- utilisateurListView -- OnItemClickListener -> CURRENT ID : " + currentID);

            }
        });
    }

    @NonNull
    private AlertDialog.Builder buildDeleteDialog() {
        Builder builder = new Builder(UtilisateurBrowseActivity.this);
        builder.setMessage(R.string.labelDeleteDialog)
                .setPositiveButton(R.string.labelYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.deleteUtilisateur(currentID);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), UtilisateurBrowseActivity.class);
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

    private ArrayList<UtilisateurEntity> makeUtilisateursList(Cursor cursor) {
        ArrayList<UtilisateurEntity> list = new ArrayList<>();
        UtilisateurEntity pe;

        cursor.moveToFirst();
        cursor.moveToPrevious();

        while (cursor.moveToNext()) {
            pe = new UtilisateurEntity();
            pe.setUtrId(cursor.getInt(cursor.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_ID)));
            pe.setUtrPrenom(cursor.getString(cursor.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_PRENOM)));
            pe.setUtrNom(cursor.getString(cursor.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_NOM)));
            pe.setUtrMail(cursor.getString(cursor.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_MAIL)));

            list.add(pe);
        }
        return list;
    }
}
