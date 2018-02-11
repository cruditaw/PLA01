package com.example.dim.pla01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dim on 09/12/2017.
 */

public class PersonDbModel extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 1;
    public static final String PERSON_TABLE_NAME = "person";
    public static final String PERSON_COLUMN_ID = "_id";
    public static final String PERSON_COLUMN_NAME = "name";
    public static final String PERSON_COLUMN_GENDER = "gender";
    public static final String PERSON_COLUMN_AGE = "age";

    public PersonDbModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PERSON_TABLE_NAME + "(" +
                PERSON_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PERSON_COLUMN_NAME + " TEXT, " +
                PERSON_COLUMN_GENDER + " TEXT, " +
                PERSON_COLUMN_AGE + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertPerson(String name, String gender, int age) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_NAME, name);
        contentValues.put(PERSON_COLUMN_GENDER, gender);
        contentValues.put(PERSON_COLUMN_AGE, age);
        db.insert(PERSON_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updatePerson(Integer id, String name, String gender, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_NAME, name);
        contentValues.put(PERSON_COLUMN_GENDER, gender);
        contentValues.put(PERSON_COLUMN_AGE, age);
        db.update(PERSON_TABLE_NAME, contentValues, PERSON_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PERSON_TABLE_NAME
                + " WHERE " + PERSON_COLUMN_ID + " =? ", new String[] { Integer.toString(id) } );
        return res;
    }

    public Cursor getAllPersons() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PERSON_TABLE_NAME, null );
        return res;
    }

    public Integer deletePerson(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PERSON_TABLE_NAME,
                PERSON_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public PersonEntity makeDummy(int cid){
        Cursor itemCursor = getPerson(cid);

        PersonEntity shadow = new PersonEntity();
//        shadow.set_id(itemCursor.getInt(itemCursor.getColumnIndex(PersonDbModel.VIN_COLUMN_ID)));
        shadow.setName(itemCursor.getString(itemCursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_NAME)));
        shadow.setGenre(itemCursor.getString(itemCursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_GENDER)));
        shadow.setAge(itemCursor.getInt(itemCursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_AGE)));
        return shadow;
    }


}
