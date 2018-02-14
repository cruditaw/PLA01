package com.example.dim.pla01.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dim on 11/02/2018.
 */

public class UtilisateurModel extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CaveavinDB.db";
    private static final int DATABASE_VERSION = 1;
    public static final String UTILISATEUR_TABLE_NAME = "utilisateur";
    public static final String UTILISATEUR_COLUMN_ID = "_id";
    public static final String UTILISATEUR_COLUMN_NOM = "nom";
    public static final String UTILISATEUR_COLUMN_PRENOM = "prenom";
    public static final String UTILISATEUR_COLUMN_MAIL = "mail";

    public UtilisateurModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + UTILISATEUR_TABLE_NAME + "(" +
                UTILISATEUR_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                UTILISATEUR_COLUMN_NOM + " TEXT, "+
                UTILISATEUR_COLUMN_PRENOM + " TEXT, "+
                UTILISATEUR_COLUMN_MAIL + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UTILISATEUR_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertUtilisateur(String nom, String prenom, String mail) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UTILISATEUR_COLUMN_NOM, nom);
        contentValues.put(UTILISATEUR_COLUMN_PRENOM, prenom);
        contentValues.put(UTILISATEUR_COLUMN_MAIL, mail);
        db.insert(UTILISATEUR_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateUtilisateur(Integer id, String nom, String prenom, String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UTILISATEUR_COLUMN_NOM, nom);
        contentValues.put(UTILISATEUR_COLUMN_PRENOM, prenom);
        contentValues.put(UTILISATEUR_COLUMN_MAIL, mail);
        db.update(UTILISATEUR_TABLE_NAME, contentValues, UTILISATEUR_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getUtilisateur(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + UTILISATEUR_TABLE_NAME + " WHERE " + UTILISATEUR_COLUMN_ID + " =? ", new String[] { Integer.toString(id) } );
        return res;
    }

    public Cursor getAllUtilisateurs() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + UTILISATEUR_TABLE_NAME, null );
        return res;
    }

    public Integer deleteUtilisateur(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(UTILISATEUR_TABLE_NAME,
                UTILISATEUR_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
