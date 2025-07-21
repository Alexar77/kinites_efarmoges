package com.example.pharmareminder.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.pharmareminder.data.model.Prescription;

import java.io.OutputStream;
import java.util.List;

public class ExportUtil {

    public static void export(Context ctx, List<Prescription> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><h1>Active Prescriptions</h1><ul>");
        for (Prescription p : list) {
            sb.append("<li>").append(p.shortName).append(" - ").append(p.timeTerm).append("</li>");
        }
        sb.append("</ul></body></html>");

        ContentValues cv = new ContentValues();
        cv.put(MediaStore.MediaColumns.DISPLAY_NAME, "prescriptions.html");
        cv.put(MediaStore.MediaColumns.MIME_TYPE, "text/html");
        cv.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
        ContentResolver resolver = ctx.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
        try (OutputStream os = resolver.openOutputStream(uri, "w")) {
            os.write(sb.toString().getBytes());
            os.flush();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
