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
import com.example.dim.pla01.entities.VigneronEntity;
import com.example.dim.pla01.entities.VinEntity;
import com.example.dim.pla01.models.TypeVinModel;
import com.example.dim.pla01.models.VigneronModel;
import com.example.dim.pla01.models.VinModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VinBrowseActivity extends AppCompatActivity {
    public final static String KEY_EXTRA_VIN_ID = "KEY_EXTRA_VIN_ID";

    private int currentID;
    private ListView vinListView;
    private List<VinEntity> vinList;
    private VinModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vin_browse);


        dbHelper = new VinModel(this);
        final Cursor cursor = dbHelper.getAllVins();

        vinList = makeVinsList(cursor);

        currentID = 0;  // list current selection index
        vinListView = (ListView) findViewById(R.id.vinListView); // list_view binding

        // list_view columns
        String[] columns = new String[]{
                VinModel.VIN_COLUMN_ID,
                VinModel.VIN_COLUMN_LIBELLE,
                VinModel.VIN_COLUMN_DOMAINE,
                VinModel.VIN_COLUMN_ANNEE,
                VinModel.VIN_COLUMN_MISEBOUTEILLE,
                VinModel.VIN_COLUMN_TYPE,
                VinModel.VIN_COLUMN_VIGNERON
        };

        // cave_item.xml layout widget name for list_view
        int[] widgets = new int[]{
                R.id.vinID,
                R.id.vinLibelle,
                R.id.vinDomaine,
                R.id.vinAnnee,
                R.id.vinMiseBouteille,
                R.id.vinType,
                R.id.vinVigneron
        };

        // setting parameters to list_view
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.vin_item, cursor, columns, widgets, 0);
        vinListView.setAdapter(cursorAdapter);

        // intent check
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            currentID = receivedIntent.getIntExtra("vinid", 0);
            System.out.println("-- VinBrowseActivity -- receivedIntent -> " + currentID);
        }

        // new_button on click listener
        ((Button) findViewById(R.id.btnVinNew)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VinBrowseActivity.this, VinCrudActivity.class);
                intent.putExtra(KEY_EXTRA_VIN_ID, 0);
                startActivity(intent);
            }
        });

        // modify_button on click listener
        ((Button) findViewById(R.id.btnVinMod)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentID > 0) {
                    Intent intent = new Intent(getApplicationContext(), VinCrudActivity.class);
                    intent.putExtra(KEY_EXTRA_VIN_ID, currentID);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Aucnune selection !", Toast.LENGTH_LONG).show();
                }
            }
        });

        // delete_button on click listener -> Dialog builder should be moved elsewhere
        ((Button) findViewById(R.id.btnCaveDel)).setOnClickListener(new OnClickListener() {
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
        vinListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) vinListView.getItemAtPosition(position);
                currentID = itemCursor.getInt(itemCursor.getColumnIndex(VinModel.VIN_COLUMN_ID));
                System.out.println("-- vinListView -- OnItemClickListener -> CURRENT ID : " + currentID);

            }
        });
    }

    @NonNull
    private Builder buildDeleteDialog() {
        Builder builder = new Builder(VinBrowseActivity.this);
        builder.setMessage(R.string.labelDeleteDialog)
                .setPositiveButton(R.string.labelYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.deleteVin(currentID);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), VinBrowseActivity.class);
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

    private ArrayList<VinEntity> makeVinsList(Cursor cursor) {
        ArrayList<VinEntity> list = new ArrayList<>();
        VinEntity pe;

        cursor.moveToFirst();
        cursor.moveToPrevious();

        while (cursor.moveToNext()) {
            pe = new VinEntity();
            pe.setVinId(cursor.getInt(cursor.getColumnIndex(VinModel.VIN_COLUMN_ID)));
            pe.setVinLibelle(cursor.getString(cursor.getColumnIndex(VinModel.VIN_COLUMN_LIBELLE)));
            pe.setVinDomaine(cursor.getString(cursor.getColumnIndex(VinModel.VIN_COLUMN_DOMAINE)));
            pe.setVinAnnee(cursor.getInt(cursor.getColumnIndex(VinModel.VIN_COLUMN_ANNEE)));
            pe.setVinMiseBouteille(new Date(cursor.getString(cursor.getColumnIndex(VinModel.VIN_COLUMN_MISEBOUTEILLE))));


            int typevinId = cursor.getInt(cursor.getColumnIndex(VinModel.VIN_COLUMN_TYPE));
            pe.setVinType(makeTypeVinFromId(typevinId));
            int vigneronID = cursor.getInt(cursor.getColumnIndex(VinModel.VIN_COLUMN_VIGNERON));
            pe.setVinVigneron(makeVigneronFromId(vigneronID));

            list.add(pe);
        }
        return list;
    }

    @NonNull
    private TypeVinEntity makeTypeVinFromId(int typevinId) {
        Cursor tv = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM " + TypeVinModel.TYPEVIN_TABLE_NAME
                + " WHERE " + TypeVinModel.TYPEVIN_COLUMN_ID + " =" + typevinId, null);
        TypeVinEntity typevin = new TypeVinEntity();
        typevin.setTvinId(tv.getInt(tv.getColumnIndex(TypeVinModel.TYPEVIN_COLUMN_ID)));
        typevin.setTvinLibelle(tv.getString(tv.getColumnIndex(TypeVinModel.TYPEVIN_COLUMN_LIBELLE)));
        return typevin;
    }

    @NonNull
    private VigneronEntity makeVigneronFromId(int vigneronId) {
        Cursor v = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM " + VigneronModel.VIGNERON_TABLE_NAME
                + " WHERE " + VigneronModel.VIGNERON_COLUMN_ID + " =" + vigneronId, null);
        VigneronEntity vigneron = new VigneronEntity();
        vigneron.setVignId(v.getInt(v.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ID)));
        vigneron.setVignAdresse(v.getString(v.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ADRESSE)));
        vigneron.setVignAdresse1(v.getString(v.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ADRESSE1)));
        vigneron.setVignAdresse2(v.getString(v.getColumnIndex(VigneronModel.VIGNERON_COLUMN_ADRESSE2)));
        vigneron.setVignCp(v.getString(v.getColumnIndex(VigneronModel.VIGNERON_COLUMN_CP)));
        vigneron.setVignVille(v.getString(v.getColumnIndex(VigneronModel.VIGNERON_COLUMN_VILLE)));
        vigneron.setVignLibelle(v.getString(v.getColumnIndex(VigneronModel.VIGNERON_COLUMN_LIBELLE)));
        vigneron.setVignMail(v.getString(v.getColumnIndex(VigneronModel.VIGNERON_COLUMN_MAIL)));
        vigneron.setVignTel(v.getString(v.getColumnIndex(VigneronModel.VIGNERON_COLUMN_TEL)));
        return vigneron;
    }

}
