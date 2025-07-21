package com.example.pharmareminder.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.pharmareminder.data.dao.PrescriptionDao;
import com.example.pharmareminder.data.model.Prescription;

@Database(entities = {Prescription.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PrescriptionDao prescriptionDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "pharma_reminder_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
