package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kharcha_theexpensesmanagement.R;

public class OutPutActivity extends AppCompatActivity {
    TextView pass_data_receive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_put);

        TextView output=findViewById(R.id.output);
        pass_data_receive=findViewById(R.id.pass_data_receive);
        Bundle bundle=getIntent().getExtras();
        String data_output=bundle.getString("output_data");
        //String data_output=getIntent().getStringExtra("output_data");
        output.setText(data_output);
    }
}