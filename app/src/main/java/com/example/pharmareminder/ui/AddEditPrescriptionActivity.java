package com.example.pharmareminder.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmareminder.R;
import com.example.pharmareminder.data.db.AppDatabase;
import com.example.pharmareminder.data.model.Prescription;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AddEditPrescriptionActivity extends AppCompatActivity {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Spinner spinner = findViewById(R.id.spinnerTerm);
        String[] terms = getResources().getStringArray(R.array.time_terms);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, terms));

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> save());
    }

    private void save() {
        EditText etName = findViewById(R.id.etShortName);
        EditText etDesc = findViewById(R.id.etDesc);
        EditText etStart = findViewById(R.id.etStart);
        EditText etEnd = findViewById(R.id.etEnd);
        EditText etDoctor = findViewById(R.id.etDoctorName);
        EditText etLoc = findViewById(R.id.etDoctorLoc);
        Spinner spinner = findViewById(R.id.spinnerTerm);

        try {
            Date start = sdf.parse(etStart.getText().toString().trim());
            Date end = sdf.parse(etEnd.getText().toString().trim());

            Prescription p = new Prescription(
                    etName.getText().toString(),
                    etDesc.getText().toString(),
                    start,
                    end,
                    spinner.getSelectedItem().toString(),
                    etDoctor.getText().toString(),
                    etLoc.getText().toString()
            );
            Executors.newSingleThreadExecutor().execute(() ->
                    AppDatabase.getInstance(this).prescriptionDao().insert(p)
            );
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
