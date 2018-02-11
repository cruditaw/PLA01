package com.example.dim.pla01.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dim.pla01.PersonEntity;
import com.example.dim.pla01.entities.VinEntity;

import java.sql.Date;

/**
 * Created by dim on 09/02/2018.
 */

public class VinModel extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "CAVDB_vin.db";
    private static final int DATABASE_VERSION = 1;
    public static final String VIN_TABLE_NAME = "vin";
    public static final String VIN_COLUMN_ID = "_id";
    public static final String VIN_COLUMN_LIBELLE = "libelle";
    public static final String VIN_COLUMN_DOMAINE = "domaine";
    public static final String VIN_COLUMN_ANNEE = "anne";
    public static final String VIN_COLUMN_MISEBOUTEILLE = "misebouteille";
    public static final String VIN_COLUMN_TYPE = "type";
    public static final String VIN_COLUMN_VIGNERON = "vigneron";

    public VinModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + VIN_TABLE_NAME + "(" +
                VIN_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                VIN_COLUMN_LIBELLE + " TEXT, " +
                VIN_COLUMN_DOMAINE + " TEXT, " +
                VIN_COLUMN_ANNEE + " TEXT, " +
                VIN_COLUMN_MISEBOUTEILLE + " TEXT, " +
                VIN_COLUMN_TYPE + " INTEGER, " +
                VIN_COLUMN_VIGNERON + " INTEGER);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VIN_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertVin(String libelle, String domaine, String annee, String mb, int typeId, int vigneronId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIN_COLUMN_LIBELLE, libelle);
        contentValues.put(VIN_COLUMN_DOMAINE, domaine);
        contentValues.put(VIN_COLUMN_ANNEE, annee);
        contentValues.put(VIN_COLUMN_MISEBOUTEILLE, mb);
        contentValues.put(VIN_COLUMN_TYPE, typeId);
        contentValues.put(VIN_COLUMN_VIGNERON, vigneronId);
        db.insert(VIN_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateVin(Integer id, String libelle, String domaine, String annee, String mb, int typeId, int vigneronId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIN_COLUMN_LIBELLE, libelle);
        contentValues.put(VIN_COLUMN_DOMAINE, domaine);
        contentValues.put(VIN_COLUMN_ANNEE, annee);
        contentValues.put(VIN_COLUMN_MISEBOUTEILLE, mb);
        contentValues.put(VIN_COLUMN_TYPE, typeId);
        contentValues.put(VIN_COLUMN_VIGNERON, vigneronId);
        db.update(VIN_TABLE_NAME, contentValues, VIN_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getVin(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + VIN_TABLE_NAME + " WHERE " + VIN_COLUMN_ID + " =? ", new String[] { Integer.toString(id) } );
        return res;
    }

    public Cursor getAllVins() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + VIN_TABLE_NAME, null );
        return res;
    }

    public Integer deleteVin(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(VIN_TABLE_NAME,
                VIN_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public VinEntity makeDummy(int cid){
        Cursor itemCursor = getVin(cid);

        VinEntity shadow = new VinEntity();
//        shadow.set_id(itemCursor.getInt(itemCursor.getColumnIndex(PersonDbModel.VIN_COLUMN_ID)));
        shadow.setVinLibelle(itemCursor.getString(itemCursor.getColumnIndex(VinModel.VIN_COLUMN_LIBELLE)));
        shadow.setVinDomaine(itemCursor.getString(itemCursor.getColumnIndex(VinModel.VIN_COLUMN_DOMAINE)));
        System.out.println("VinModel : makeDummy . May throw Null Pointer ! -> Date.valueOf() uninitialized  ! Format may be necessary");
        shadow.setVinMiseBouteille(Date.valueOf(itemCursor.getString(itemCursor.getColumnIndex(VinModel.VIN_COLUMN_MISEBOUTEILLE))));
        return shadow;
    }
}
