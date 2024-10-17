package com.ducnm.btvn_sqliteopenhelper.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.ducnm.btvn_sqliteopenhelper.database.ProductDatabase;

public class MyContentProvider extends ContentProvider {

    private static final int PRODUCTS = 100;
    private static final int PRODUCT_ID = 101;

    private ProductDatabase productDatabase;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // URI for all products
        uriMatcher.addURI("com.ducnm.btvn_sqliteopenhelper", "product", PRODUCTS);
        // URI for single product by id
        uriMatcher.addURI("com.ducnm.btvn_sqliteopenhelper", "product/#", PRODUCT_ID);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                return "vnd.android.cursor.dir/vnd.com.ducnm.btvn_sqliteopenhelper.product";
            case PRODUCT_ID:
                return "vnd.android.cursor.item/vnd.com.ducnm.btvn_sqliteopenhelper.product";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        productDatabase = ProductDatabase.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                cursor = productDatabase.productDAO().getAllProductsCursor();
                break;
            case PRODUCT_ID:
                long id = ContentUris.parseId(uri);
                cursor = productDatabase.productDAO().getProductByIdCursor(id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}