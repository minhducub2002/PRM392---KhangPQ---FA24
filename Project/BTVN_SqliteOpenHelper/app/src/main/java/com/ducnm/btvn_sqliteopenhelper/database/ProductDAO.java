package com.ducnm.btvn_sqliteopenhelper.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ducnm.btvn_sqliteopenhelper.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM product")
    List<Product> getAllProduct();

    @Query("SELECT * FROM product WHERE productName = :productName")
    List<Product> checkProduct(String productName);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("DELETE FROM product")
    void deleteAllProduct();

    @Query("SELECT * FROM product")
    Cursor getAllProductsCursor();

    @Query("SELECT * FROM product WHERE id = :id")
    Cursor getProductByIdCursor(long id);
}
