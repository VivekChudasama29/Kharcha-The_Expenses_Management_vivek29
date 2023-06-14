package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.R;
import com.example.kharcha_theexpensesmanagement.pdf_generate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Final_Settlement2 extends AppCompatActivity {
    String whopaidmembernm = "";
    FirebaseAuth auth;
    FirebaseFirestore db;
    TextView group_id, group_nm;
    AppCompatButton DoneBtn;
    Double totalexpense = 0.0;
    TextView totalOwes_counter;
    TextView total_balance_counter;
    Double Totalpaid = 0.0;
    ProgressDialog progressDialog;
    String str = "";
    ImageView settlement2;
    Button generate;

    boolean doublePress = false;
    Button btnCreate;
    public final int REQUEST_CODE = 100;
    int pageWidth = 720;
    int pageHeight = 1200;
    Bitmap imageBitmap, scaledImageBitmap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_settlement2);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data......");
        progressDialog.isShowing();

        group_id = findViewById(R.id.passgroup_id2);
        group_nm = findViewById(R.id.passwho_paid2);
        // totalexpenses_counter = findViewById(R.id.Totalexpensescount2);
        // totalOwes_counter = findViewById(R.id.total_owes_counter2);
        total_balance_counter = findViewById(R.id.total_balance_counter2);
        settlement2 = findViewById(R.id.back_balance2);

        imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.repe);
        scaledImageBitmap = Bitmap.createScaledBitmap(imageBitmap, 70, 63, false);


        generate = findViewById(R.id.generate_pdf);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPDF();
            }
        });
        // DoneBtn = findViewById(R.id.Done2);

        Bundle b = getIntent().getExtras();
        group_id.setText(b.getString("GroupIddata2"));
        group_nm.setText(b.getString("GroupNamedata2"));

        db.collection("member_info").whereEqualTo("group_id", group_id.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                Map<String, Double> whoPaid = new HashMap<>();
                Map<String, Double> whoOwes = new HashMap<>();

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult().getDocuments().get(0);

                    String MemID = document.getId();
                    HashMap<String, Double> hashMap = (HashMap<String, Double>) document.get("owns");
                    for (Map.Entry<String, Double> e : hashMap.entrySet()) {
                        if (e.getValue() < 0.0) {
                            whoOwes.put(e.getKey(), -e.getValue());
                            System.out.println("Minus  Member :: " + e.getKey() + "  " + e.getValue());

                        } else if (e.getValue() > 0) {
                            whoPaid.put(e.getKey(), e.getValue());
                            System.out.println("Plus Member :: " + e.getKey() + " " + e.getValue());
                        }
                    }
                }
                System.out.println("who owns :");
                System.out.println(whoOwes);
                System.out.println("who paid :");
                System.out.println(whoPaid);
                Map<String, Double> settlement = new HashMap<>();
                for (Map.Entry<String, Double> entry : whoPaid.entrySet()) {
                    String paidBy = entry.getKey();
                    double amountPaid = entry.getValue();
                    for (Map.Entry<String, Double> owesEntry : whoOwes.entrySet()) {
                        String owesTo = owesEntry.getKey();
                        double amountOwed = owesEntry.getValue();
                        if (amountPaid >= amountOwed) {

                            settlement.put(owesTo + "\t -> \t" + paidBy, Double.parseDouble(new DecimalFormat("##.##").format(amountOwed)));
                           // Toast.makeText(Final_Settlement2.this, whopaidmembernm, Toast.LENGTH_SHORT).show();

                            amountPaid -= amountOwed;
                            owesEntry.setValue(0.0);
                        } else {

                            settlement.put(owesTo + "\t -> \t" + paidBy, Double.parseDouble(new DecimalFormat("##.##").format(amountPaid)));

                            amountOwed -= amountPaid;
                            owesEntry.setValue(amountOwed);
                            break;
                        }
                    }
                }
                if (!settlement.isEmpty()) {
                    for (Map.Entry<String, Double> entry : settlement.entrySet()) {
                        String description = entry.getKey();
                        double amount = entry.getValue();
                        str += "  " + description + " \t : \t" + amount + "\n" + "\n" + "\n";
                        System.out.println(description + ": " + amount);
                    }
                    total_balance_counter.setText(str);
                } else {
                    total_balance_counter.setText("No Activity");

                }
            }
        });
//        DoneBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i1=new Intent(Final_Settlement2.this, navigationbar.class);
//                startActivity(i1);
//                finishAffinity();
//            }
//        });
        settlement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Final_Settlement2.this, navigationbar.class);
                startActivity(i1);
                finishAffinity();
            }
        });
    }

    private void createPDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();


        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        canvas.drawBitmap(scaledImageBitmap, 320, 20, paint);

        paint.setColor(Color.BLACK);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);
        paint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        canvas.drawText(group_nm.getText().toString(),350,140,paint);
        paint.setTextSize(40);

        canvas.drawText("Group Expenses Settlement ",380,200,paint);

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(32);
        int lineWidth = 800;

// Create a StaticLayout object to break the text into multiple lines
        StaticLayout staticLayout = new StaticLayout(str, textPaint, lineWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

// Draw the StaticLayout on the canvas
        canvas.save();
        canvas.translate(20, 250); // Move the text to a specific position on the canvas
        staticLayout.draw(canvas);
        pdfDocument.finishPage(page);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "/Kharcha" +System.currentTimeMillis()+ ".pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();


    }

    private void requestAllPermission() {

        ActivityCompat.requestPermissions(Final_Settlement2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Final_Settlement2.this, "Permission Granted", Toast.LENGTH_SHORT).show();

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