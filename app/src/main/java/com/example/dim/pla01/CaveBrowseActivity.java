package com.example.dim.pla01;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.dim.pla01.entities.CaveEntity;
import com.example.dim.pla01.entities.TypeVinEntity;
import com.example.dim.pla01.entities.UtilisateurEntity;
import com.example.dim.pla01.entities.VigneronEntity;
import com.example.dim.pla01.entities.VinEntity;
import com.example.dim.pla01.models.CaveModel;
import com.example.dim.pla01.models.TypeVinModel;
import com.example.dim.pla01.models.UtilisateurModel;
import com.example.dim.pla01.models.VigneronModel;
import com.example.dim.pla01.models.VinModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cdsm on 2/14/18.
 */

public class CaveBrowseActivity extends AppCompatActivity {
    public final static String KEY_EXTRA_CAVE_ID = "KEY_EXTRA_CAVE_ID";

    private int currentID;
    private ListView caveListView;
    private List<CaveEntity> caveList;
    CaveModel dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_browse);

        dbHelper = new CaveModel(this);
        final Cursor cursor = dbHelper.getAllCaves();

        caveList = makeCavesList(cursor);

        currentID = 0;  // list current selection index
        caveListView = (ListView) findViewById(R.id.personListView); // list_view binding


        // list_view columns
        String[] columns = new String[]{
                CaveModel.CAVE_COLUMN_ID,
                CaveModel.CAVE_COLUMN_VIN,
                CaveModel.CAVE_COLUMN_UTILISATEUR,
                CaveModel.CAVE_COLUMN_NBOUTEILLES
        };
    }

    private ArrayList<CaveEntity> makeCavesList(Cursor cursor) {
        ArrayList<CaveEntity> list = new ArrayList<>();
        CaveEntity pe;

        cursor.moveToFirst();
        cursor.moveToPrevious();

        while (cursor.moveToNext()) {
            pe = new CaveEntity();
            pe.setCavId(cursor.getInt(cursor.getColumnIndex(CaveModel.CAVE_COLUMN_ID)));

            int vinId = cursor.getInt(cursor.getColumnIndex(CaveModel.CAVE_COLUMN_VIN));
            pe.setCavVin(makeVinFromId(vinId));

            int utilisateurId = cursor.getInt(cursor.getColumnIndex(CaveModel.CAVE_COLUMN_UTILISATEUR));
            pe.setCavUtilisateur(makeUtilisateurFromId(utilisateurId));

            pe.setCavNombreBouteilles(cursor.getInt(cursor.getColumnIndex(CaveModel.CAVE_COLUMN_NBOUTEILLES)));

            list.add(pe);
        }
        return list;
    }

    @NonNull
    private UtilisateurEntity makeUtilisateurFromId(int utilisateurId) {
        final Cursor u =  dbHelper.getReadableDatabase().rawQuery( "SELECT * FROM " + UtilisateurModel.UTILISATEUR_TABLE_NAME
                +" WHERE "+ UtilisateurModel.UTILISATEUR_COLUMN_ID +" ="+utilisateurId, null);
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setUtrId(u.getInt(u.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_ID)));
        utilisateur.setUtrMail(u.getString(u.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_MAIL)));
        utilisateur.setUtrNom(u.getString(u.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_NOM)));
        utilisateur.setUtrPrenom(u.getString(u.getColumnIndex(UtilisateurModel.UTILISATEUR_COLUMN_PRENOM)));
        return utilisateur;
    }

    @NonNull
    private VinEntity makeVinFromId(int vinId) {
        Cursor res = dbHelper.getReadableDatabase().rawQuery( "SELECT * FROM " + VinModel.VIN_TABLE_NAME
                +" WHERE "+ VinModel.VIN_COLUMN_ID +" ="+vinId, null);
        VinEntity vin = new VinEntity();
        vin.setVinId(res.getInt(res.getColumnIndex(VinModel.VIN_COLUMN_ID)));
        vin.setVinAnnee(res.getInt(res.getColumnIndex(VinModel.VIN_COLUMN_ANNEE)));
        vin.setVinDomaine(res.getString(res.getColumnIndex(VinModel.VIN_COLUMN_DOMAINE)));
        vin.setVinLibelle(res.getString(res.getColumnIndex(VinModel.VIN_COLUMN_LIBELLE)));
        // Date Mise Bouteille a faire !

        int typevinId = res.getInt(res.getColumnIndex(VinModel.VIN_COLUMN_TYPE));
        vin.setVinType(makeTypeVinFromId(typevinId));

        int vigneronId = res.getInt(res.getColumnIndex(VinModel.VIN_COLUMN_VIGNERON));
        vin.setVinVigneron(makeVigneronFromId(vigneronId));
        return vin;
    }

    @NonNull
    private TypeVinEntity makeTypeVinFromId(int typevinId) {
        Cursor tv = dbHelper.getReadableDatabase().rawQuery( "SELECT * FROM " + TypeVinModel.TYPEVIN_TABLE_NAME
                +" WHERE "+ TypeVinModel.TYPEVIN_COLUMN_ID +" ="+typevinId, null);
        TypeVinEntity typevin = new TypeVinEntity();
        typevin.setTvinId(tv.getInt(tv.getColumnIndex(TypeVinModel.TYPEVIN_COLUMN_ID)));
        typevin.setTvinLibelle(tv.getString(tv.getColumnIndex(TypeVinModel.TYPEVIN_COLUMN_LIBELLE)));
        return typevin;
    }

    @NonNull
    private VigneronEntity makeVigneronFromId(int vigneronId) {
        Cursor v = dbHelper.getReadableDatabase().rawQuery( "SELECT * FROM " + VigneronModel.VIGNERON_TABLE_NAME
                +" WHERE "+ VigneronModel.VIGNERON_COLUMN_ID+" ="+vigneronId, null);
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
