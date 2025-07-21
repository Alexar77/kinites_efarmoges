package com.example.pharmareminder.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmareminder.R;
import com.example.pharmareminder.data.db.AppDatabase;
import com.example.pharmareminder.data.model.Prescription;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "id";

    public static void start(Context ctx, long id) {
        Intent intent = new Intent(ctx, DetailActivity.class);
        intent.putExtra(EXTRA_ID, id);
        ctx.startActivity(intent);
    }

    private Prescription prescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        Executors.newSingleThreadExecutor().execute(() -> {
            prescription = AppDatabase.getInstance(this).prescriptionDao()
                    .getAll().getValue().stream().filter(p -> p.id == id).findFirst().orElse(null);
            runOnUiThread(() -> populateUI());
        });
    }

    private void populateUI() {
        if (prescription == null) return;
        ((TextView) findViewById(R.id.textShortName)).setText(prescription.shortName);
        ((TextView) findViewById(R.id.textDesc)).setText(prescription.description);
        ((TextView) findViewById(R.id.textTime)).setText(prescription.timeTerm);
        ((TextView) findViewById(R.id.textDoctor)).setText(prescription.doctorName);
        ((TextView) findViewById(R.id.textActive)).setText(String.valueOf(prescription.isActive));
        ((TextView) findViewById(R.id.textReceivedToday)).setText(String.valueOf(prescription.hasReceivedToday));
        Button btnReceive = findViewById(R.id.btnReceive);
        btnReceive.setOnClickListener(v -> markReceived());
        Button btnMap = findViewById(R.id.btnMap);
        btnMap.setEnabled(prescription.doctorLocation != null && !prescription.doctorLocation.isEmpty());
        btnMap.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(prescription.doctorLocation));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
        });
    }

    private void markReceived() {
        Date now = new Date();
        prescription.lastDateReceived = now;
        prescription.hasReceivedToday = true;
        Executors.newSingleThreadExecutor().execute(() ->
            AppDatabase.getInstance(this).prescriptionDao().update(prescription)
        );
        finish();
    }
}
