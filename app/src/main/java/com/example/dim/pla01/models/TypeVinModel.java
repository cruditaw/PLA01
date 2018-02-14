package com.example.dim.pla01.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dim on 11/02/2018.
 */

public class TypeVinModel extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CaveavinDB.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TYPEVIN_TABLE_NAME = "vin";
    public static final String TYPEVIN_COLUMN_ID = "_id";
    public static final String TYPEVIN_COLUMN_LIBELLE = "libelle";

    public TypeVinModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TYPEVIN_TABLE_NAME + "(" +
                TYPEVIN_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                TYPEVIN_COLUMN_LIBELLE + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TYPEVIN_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertTypeVin(String libelle) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TYPEVIN_COLUMN_LIBELLE, libelle);
        db.insert(TYPEVIN_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateTypeVin(Integer id, String libelle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TYPEVIN_COLUMN_LIBELLE, libelle);
        db.update(TYPEVIN_TABLE_NAME, contentValues, TYPEVIN_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getTypeVin(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TYPEVIN_TABLE_NAME + " WHERE " + TYPEVIN_COLUMN_ID + " =? ", new String[] { Integer.toString(id) } );
        return res;
    }

    public Cursor getAllTypeVins() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TYPEVIN_TABLE_NAME, null );
        return res;
    }

    public Integer deleteTypeVin(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TYPEVIN_TABLE_NAME,
                TYPEVIN_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
