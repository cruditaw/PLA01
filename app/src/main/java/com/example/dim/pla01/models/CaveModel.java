package com.example.dim.pla01.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dim on 11/02/2018.
 */

public class CaveModel extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "CAVDB_cave.db";
    private static final int DATABASE_VERSION = 1;
    public static final String CAVE_TABLE_NAME = "cave";
    public static final String CAVE_COLUMN_ID = "_id";
    public static final String CAVE_COLUMN_VIN = "vin";
    public static final String CAVE_COLUMN_UTILISATEUR = "utilisateur";
    public static final String CAVE_COLUMN_NBOUTEILLES = "nbouteilles";

    public CaveModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + CAVE_TABLE_NAME + "(" +
                CAVE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CAVE_COLUMN_VIN + " INTEGER, "+
                CAVE_COLUMN_UTILISATEUR + " INTEGER, "+
                CAVE_COLUMN_NBOUTEILLES + " INTEGER);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CAVE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertCave(int vinId, int utilisateurId, int nb) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAVE_COLUMN_VIN, vinId);
        contentValues.put(CAVE_COLUMN_UTILISATEUR, utilisateurId);
        contentValues.put(CAVE_COLUMN_NBOUTEILLES, nb);
        db.insert(CAVE_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateCave(Integer id, Integer vinId, Integer utilisateurId, int nb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAVE_COLUMN_VIN, vinId);
        contentValues.put(CAVE_COLUMN_UTILISATEUR, utilisateurId);
        contentValues.put(CAVE_COLUMN_NBOUTEILLES, nb);
        db.update(CAVE_TABLE_NAME, contentValues, CAVE_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getUtilisateur(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CAVE_TABLE_NAME + " WHERE " + CAVE_COLUMN_ID + " =? ", new String[] { Integer.toString(id) } );
        return res;
    }

    public Cursor getAllUtilisateurs() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CAVE_TABLE_NAME, null );
        return res;
    }

    public Integer deleteUtilisateur(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CAVE_TABLE_NAME,
                CAVE_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
