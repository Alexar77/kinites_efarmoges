package com.example.pharmareminder.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pharmareminder.data.model.Prescription;

import java.util.Date;
import java.util.List;

@Dao
public interface PrescriptionDao {

    @Insert
    long insert(Prescription prescription);

    @Update
    void update(Prescription prescription);

    @Delete
    void delete(Prescription prescription);

    @Query("DELETE FROM prescriptions WHERE id = :id")
    int deleteById(long id);

    @Query("SELECT * FROM prescriptions ORDER BY timeTerm")
    LiveData<List<Prescription>> getAll();

    @Query("SELECT * FROM prescriptions WHERE isActive = 1 ORDER BY timeTerm")
    LiveData<List<Prescription>> getActive();

    @Query("UPDATE prescriptions SET isActive = (:state) WHERE id = :id")
    void updateIsActive(long id, boolean state);

    @Query("UPDATE prescriptions SET hasReceivedToday = :value, lastDateReceived = :date WHERE id = :id")
    void markReceivedToday(long id, boolean value, Date date);
}
