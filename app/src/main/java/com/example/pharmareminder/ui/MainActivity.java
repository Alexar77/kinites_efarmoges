package com.example.pharmareminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pharmareminder.R;
import com.example.pharmareminder.worker.PrescriptionUpdateWorker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    PrescriptionViewModel viewModel;
    PrescriptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new PrescriptionAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(PrescriptionViewModel.class);
        viewModel.getActivePrescriptions().observe(this, adapter::submitList);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> startActivity(new Intent(this, AddEditPrescriptionActivity.class)));

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                PrescriptionUpdateWorker.class, 1, TimeUnit.HOURS).build();
        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork("updateWorker", ExistingPeriodicWorkPolicy.UPDATE, workRequest);
    }
}
