package com.example.pharmareminder.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.pharmareminder.data.db.AppDatabase;
import com.example.pharmareminder.data.model.Prescription;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

public class PrescriptionUpdateWorker extends Worker {

    public PrescriptionUpdateWorker(@NonNull Context context,
                                    @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        Executors.newSingleThreadExecutor().execute(() -> {
            for (Prescription p : db.prescriptionDao().getAll().getValue()) {
                Date now = new Date();
                boolean active = now.after(p.startDate) && now.before(p.endDate);
                p.isActive = active;

                Calendar calNow = Calendar.getInstance();
                Calendar calLast = Calendar.getInstance();
                if (p.lastDateReceived != null) {
                    calLast.setTime(p.lastDateReceived);
                }
                boolean sameDay = p.lastDateReceived != null &&
                        calNow.get(Calendar.YEAR) == calLast.get(Calendar.YEAR) &&
                        calNow.get(Calendar.DAY_OF_YEAR) == calLast.get(Calendar.DAY_OF_YEAR);
                p.hasReceivedToday = sameDay;
                db.prescriptionDao().update(p);
            }
        });
        return Result.success();
    }
}
