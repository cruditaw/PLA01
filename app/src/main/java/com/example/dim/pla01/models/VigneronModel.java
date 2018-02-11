package com.example.dim.pla01.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dim on 11/02/2018.
 */

public class VigneronModel extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CAVDB_vigneron.db";
    private static final int DATABASE_VERSION = 1;
    public static final String VIGNERON_TABLE_NAME = "vigneron";
    public static final String VIGNERON_COLUMN_ID = "_id";
    public static final String VIGNERON_COLUMN_LIBELLE = "libelle";
    public static final String VIGNERON_COLUMN_ADRESSE = "adresse";
    public static final String VIGNERON_COLUMN_ADRESSE1 = "adresse1";
    public static final String VIGNERON_COLUMN_ADRESSE2 = "adresse2";
    public static final String VIGNERON_COLUMN_VILLE = "ville";
    public static final String VIGNERON_COLUMN_CP = "cp";
    public static final String VIGNERON_COLUMN_TEL = "tel";
    public static final String VIGNERON_COLUMN_MAIL = "mail";

    public VigneronModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + VIGNERON_TABLE_NAME + "(" +
                VIGNERON_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                VIGNERON_COLUMN_LIBELLE + " TEXT, " +
                VIGNERON_COLUMN_ADRESSE + " TEXT, " +
                VIGNERON_COLUMN_ADRESSE1 + " TEXT, " +
                VIGNERON_COLUMN_ADRESSE2 + " TEXT, " +
                VIGNERON_COLUMN_VILLE + " TEXT, " +
                VIGNERON_COLUMN_CP + " TEXT, " +
                VIGNERON_COLUMN_TEL + " TEXT, " +
                VIGNERON_COLUMN_MAIL + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VIGNERON_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertVigneron(String libelle, String adresse, String adresse1, String adresse2, String ville, String cp, String tel, String mail) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIGNERON_COLUMN_LIBELLE, libelle);
        contentValues.put(VIGNERON_COLUMN_ADRESSE, adresse);
        contentValues.put(VIGNERON_COLUMN_ADRESSE1, adresse1);
        contentValues.put(VIGNERON_COLUMN_ADRESSE2, adresse2);
        contentValues.put(VIGNERON_COLUMN_VILLE, ville);
        contentValues.put(VIGNERON_COLUMN_CP, cp);
        contentValues.put(VIGNERON_COLUMN_TEL, tel);
        contentValues.put(VIGNERON_COLUMN_MAIL, mail);
        db.insert(VIGNERON_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateVigneron(Integer id, String libelle, String adresse, String adresse1, String adresse2, String ville, String cp, String tel, String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIGNERON_COLUMN_LIBELLE, libelle);
        contentValues.put(VIGNERON_COLUMN_ADRESSE, adresse);
        contentValues.put(VIGNERON_COLUMN_ADRESSE1, adresse1);
        contentValues.put(VIGNERON_COLUMN_ADRESSE2, adresse2);
        contentValues.put(VIGNERON_COLUMN_VILLE, ville);
        contentValues.put(VIGNERON_COLUMN_CP, cp);
        contentValues.put(VIGNERON_COLUMN_TEL, tel);
        contentValues.put(VIGNERON_COLUMN_MAIL, mail);
        db.update(VIGNERON_TABLE_NAME, contentValues, VIGNERON_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getVigneron(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + VIGNERON_TABLE_NAME + " WHERE " + VIGNERON_COLUMN_ID + " =? ", new String[] { Integer.toString(id) } );
        return res;
    }

    public Cursor getAllVignerons() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + VIGNERON_TABLE_NAME, null );
        return res;
    }

    public Integer deleteVigneron(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(VIGNERON_TABLE_NAME,
                VIGNERON_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
