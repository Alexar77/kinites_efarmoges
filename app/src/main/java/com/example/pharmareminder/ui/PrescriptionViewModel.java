package com.example.pharmareminder.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pharmareminder.data.db.AppDatabase;
import com.example.pharmareminder.data.model.Prescription;

import java.util.List;

public class PrescriptionViewModel extends AndroidViewModel {

    private final LiveData<List<Prescription>> active;

    public PrescriptionViewModel(@NonNull Application application) {
        super(application);
        active = AppDatabase.getInstance(application)
                .prescriptionDao()
                .getActive();
    }

    public LiveData<List<Prescription>> getActivePrescriptions() {
        return active;
    }
}
