package com.ducnm.tramsacxedien.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ducnm.tramsacxedien.object.Account;

@Database(entities = {Account.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "TramSacXeDien.db";
    private static AccountDatabase instance;

    public static synchronized AccountDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AccountDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract AccountDao accountDao();
}
