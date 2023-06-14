package com.example.kharcha_theexpensesmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class pdf_generate extends AppCompatActivity {
    private EditText myEditText;
    Button generate;
    boolean doublePress = false;
    Button btnCreate;
    public final int REQUEST_CODE = 100;
    int pageWidth = 720;
    int pageHeight = 1200;
    Bitmap imageBitmap, scaledImageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_generate);


        imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.repe);
        scaledImageBitmap = Bitmap.createScaledBitmap(imageBitmap, 70, 57, false);

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    createPDF();
                } else {
                    requestAllPermission();
                }
            }
        });

    }

    private void createPDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        canvas.drawBitmap(scaledImageBitmap, 0, 0, paint);



        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);
        paint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("", pageWidth / 2, 200, paint);

        Paint line = new Paint();
        line.setStrokeWidth(2f);

        // horizontal lines
        canvas.drawLine(150, 350, 550, 350, line);
        canvas.drawLine(150, 400, 550, 400, line);
        canvas.drawLine(150, 450, 550, 450, line);
        canvas.drawLine(150, 500, 550, 500, line);
        canvas.drawLine(150, 550, 550, 550, line);



        // vertical lines
        canvas.drawLine(150, 350, 150, 550, line);
        canvas.drawLine(350, 350, 350, 550, line);
        canvas.drawLine(550, 350, 550, 550, line);






        pdfDocument.finishPage(page);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "/Kharcha" + ".pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();


    }

    private void requestAllPermission() {

        ActivityCompat.requestPermissions(pdf_generate.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(pdf_generate.this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (doublePress) {
            super.onBackPressed();
            return;
        }
        this.doublePress = true;
        Toast.makeText(this, "Press again to exit app", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doublePress = false;
            }
        }, 2000);
    }
}