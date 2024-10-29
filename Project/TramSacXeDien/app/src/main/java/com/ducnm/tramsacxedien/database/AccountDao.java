package com.ducnm.tramsacxedien.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ducnm.tramsacxedien.object.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert
    void insertAccount(Account account);

    @Query("SELECT * FROM account")
    List<Account> getAllAccounts();

    @Query("SELECT * FROM account WHERE username = :username")
    List<Account> getAccountByUsername(String username);

    @Update
    void updateAccount(Account account);

    @Delete
    void deleteAccount(Account account);

    @Query("DELETE FROM account WHERE username = :username")
    void deleteAccountByUsername(String username);

    @Query("SELECT * FROM account")
    Cursor getAllAccountsCursor();

    @Query("SELECT * FROM account WHERE id = :id")
    Cursor getAccountByUsernameCursor(int id);
}
