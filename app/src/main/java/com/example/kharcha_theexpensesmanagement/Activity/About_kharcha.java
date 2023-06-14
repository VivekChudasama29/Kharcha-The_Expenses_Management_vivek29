package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kharcha_theexpensesmanagement.R;

public class About_kharcha extends AppCompatActivity {
ImageView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_kharcha);

        TextView textView = new TextView(About_kharcha.this);

        textView.setPadding(16, 16, 16, 16);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[] {50, 50, 50, 50, 0, 0, 0, 0});
        gradientDrawable.setColor(Color.WHITE);

        textView.setBackground(gradientDrawable);

        Back=findViewById(R.id.back_profile);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(About_kharcha.this,navigationbar.class);
                startActivity(i1);
                finish();
            }
        });

    }
}