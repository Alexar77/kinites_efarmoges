package com.example.pharmareminder.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.pharmareminder.data.db.AppDatabase;
import com.example.pharmareminder.data.model.Prescription;

public class PrescriptionContentProvider extends ContentProvider {

    public static final String AUTH = "com.example.pharmareminder.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTH + "/prescriptions");

    private static final int CODE_ALL = 1;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTH, "prescriptions", CODE_ALL);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (matcher.match(uri) == CODE_ALL) {
            return AppDatabase.getInstance(getContext()).query("SELECT * FROM prescriptions", null);
        }
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (matcher.match(uri) == CODE_ALL) {
            Prescription p = new Prescription(
                    values.getAsString("shortName"),
                    values.getAsString("description"),
                    new java.util.Date(values.getAsLong("startDate")),
                    new java.util.Date(values.getAsLong("endDate")),
                    values.getAsString("timeTerm"),
                    values.getAsString("doctorName"),
                    values.getAsString("doctorLocation")
            );
            long id = AppDatabase.getInstance(getContext()).prescriptionDao().insert(p);
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        throw new IllegalArgumentException("Insert: unknown URI" + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/prescriptions";
    }
}
