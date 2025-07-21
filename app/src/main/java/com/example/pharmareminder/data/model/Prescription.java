package com.example.pharmareminder.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "prescriptions")
public class Prescription {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String shortName;
    public String description;
    public Date startDate;
    public Date endDate;
    public String timeTerm; // see TimeTerm enum
    public String doctorName;
    public String doctorLocation;

    public boolean isActive;
    public Date lastDateReceived;
    public boolean hasReceivedToday;

    public Prescription(String shortName,
                        String description,
                        Date startDate,
                        Date endDate,
                        String timeTerm,
                        String doctorName,
                        String doctorLocation) {

        this.shortName = shortName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeTerm = timeTerm;
        this.doctorName = doctorName;
        this.doctorLocation = doctorLocation;

        this.isActive = false;
        this.lastDateReceived = null;
        this.hasReceivedToday = false;
    }
}
