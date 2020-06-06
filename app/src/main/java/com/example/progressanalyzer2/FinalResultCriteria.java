package com.example.progressanalyzer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalResultCriteria extends AppCompatActivity {
Button backtoresult;
TextView lesscgpa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result_criteria);
        lesscgpa=(TextView)findViewById(R.id.lowcgpa);
        lesscgpa.setText("< 4");
        backtoresult=(Button)findViewById(R.id.backonres);
        backtoresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Result.class);
                startActivity(intent);
            }
        });
    }
}
