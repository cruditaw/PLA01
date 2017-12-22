package com.example.dim.pla01;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class PersonManager {


    private PersonEntity curentPerson;
    private int currentID;
    private PersonDbModel personHelper;
    private List<PersonEntity> personsList;

    public PersonManager(Context context){
        initPersonModel(context);
    }

    private void initPersonModel(Context context) {
        personHelper = new PersonDbModel(context);
        currentID = 0;

        final Cursor cursor = personHelper.getAllPersons(); // persons model init
        personsList = new ArrayList<>();

        cursor.moveToFirst();
        while (cursor.moveToNext()) {

            PersonEntity pe = new PersonEntity();
            pe.setName(cursor.getString(cursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_NAME)));
            pe.setGenre(cursor.getString(cursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_GENDER)));
            pe.setAge(cursor.getInt(cursor.getColumnIndex(PersonDbModel.PERSON_COLUMN_AGE)));

            personsList.add(pe);
        }

        String [] columns = new String[] {  // listeview columns
                PersonDbModel.PERSON_COLUMN_ID,
                PersonDbModel.PERSON_COLUMN_NAME,
                PersonDbModel.PERSON_COLUMN_GENDER,
                PersonDbModel.PERSON_COLUMN_AGE
        };

        //
        int [] widgets = new int[] { // person_item.xml layout widget name for listview
                R.id.personID,
                R.id.personName,
                R.id.personGender,
                R.id.personAge
        };
    }
}
