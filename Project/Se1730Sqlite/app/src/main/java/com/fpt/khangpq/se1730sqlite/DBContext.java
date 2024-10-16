package com.fpt.khangpq.se1730sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBContext extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "mydb";

    public static final String TB_DICTIONARY = "dictionary";
    public static final String TB_DICTIONARY_ID = "id";
    public static final String TB_DICTIONARY_WORD = "word";
    public static final String TB_DICTIONARY_DEFINITION = "definition";

    public DBContext(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table dictionary\n" +
                "(\n" +
                "    id         integer not null\n" +
                "        constraint dictionary_pk\n" +
                "            primary key autoincrement,\n" +
                "    word       text,\n" +
                "    definition text\n" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        if (oldVer <= 1 && newVer >= 2) {

            System.out.println("1->2");

        }

        if (oldVer <= 2 && newVer >= 3) {

            System.out.println("2->3");

        }

        if (oldVer <= 3 && newVer >= 4) {

            System.out.println("3->4");

        }

    }


    public void insert(String word, String definition) {
        String sql = "INSERT INTO dictionary (word, definition) VALUES (?, ?)";
        getWritableDatabase().execSQL(sql, new String[]{word, definition});
    }

    public long insert2(String word, String definition) {
        ContentValues values = new ContentValues();
        values.put(TB_DICTIONARY_WORD, word);
        values.put(TB_DICTIONARY_DEFINITION, definition);
        return getWritableDatabase().insert(TB_DICTIONARY, null, values);
    }

    public Cursor getAll() {
        String sql = "SELECT * FROM dictionary";
        return getReadableDatabase().rawQuery(sql, null);
    }
}
