package com.example.progressanalyzer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProgress extends AppCompatActivity {
    TextView sem1p, sem1g, sem2p, sem2g, sem3p, sem3g, sem4p, sem4g, sem5p, sem5g, atkts1, atkts2, atkts3, atkts4, atkts5;
    Button homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progress);
        sem1p = (TextView) findViewById(R.id.s1sgpavalue);
        sem1g = (TextView) findViewById(R.id.s1gradevalue);
        sem2p = (TextView) findViewById(R.id.s2sgpavalue);
        sem2g = (TextView) findViewById(R.id.s2gradevalue);
        sem3p = (TextView) findViewById(R.id.s3sgpavalue);
        sem3g = (TextView) findViewById(R.id.s3gradevalue);
        sem4p = (TextView) findViewById(R.id.s4sgpavalue);
        sem4g = (TextView) findViewById(R.id.s4gradevalue);
        sem5p = (TextView) findViewById(R.id.s5sgpavalue);
        sem5g = (TextView) findViewById(R.id.s5gradevalue);
        atkts1 = (TextView) findViewById(R.id.atkts1);
        atkts1.setText("");
        atkts2 = (TextView) findViewById(R.id.atkts2);
        atkts2.setText("");
        atkts3 = (TextView) findViewById(R.id.atkts3);
        atkts3.setText("");
        atkts4 = (TextView) findViewById(R.id.atkts4);
        atkts4.setText("");
        atkts5 = (TextView) findViewById(R.id.atkts5);
        atkts5.setText("");
        homepage = (Button) findViewById(R.id.homeredirect);
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Sem1");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        if (Float.parseFloat(ds.child("sgpa").getValue().toString()) < 4 &&
                                (ds.child("grade").getValue().toString().equals("F") || ds.child("grade").getValue().toString().equals("AB"))) {
                            atkts1.setText("Semester 1 ATKT");
                        }
                        Float sgpa = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        sem1p.setText(String.format("%.1f", sgpa));
                        sem1g.setText(ds.child("grade").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Sem2");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        if (Float.parseFloat(ds.child("sgpa").getValue().toString()) < 4 &&
                                (ds.child("grade").getValue().toString().equals("F") || ds.child("grade").getValue().toString().equals("AB"))) {
                            atkts2.setText("Semester 2 ATKT");
                        }
                        Float sgpa = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        sem2p.setText(String.format("%.1f", sgpa));
                        sem2g.setText(ds.child("grade").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Sem3");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        if (Float.parseFloat(ds.child("sgpa").getValue().toString()) < 4 &&
                                (ds.child("grade").getValue().toString().equals("F") || ds.child("grade").getValue().toString().equals("AB"))) {
                            atkts3.setText("Semester 3 ATKT");
                        }
                        Float sgpa = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        sem3p.setText(String.format("%.1f", sgpa));
                        sem3g.setText(ds.child("grade").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Sem4");
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        if (Float.parseFloat(ds.child("sgpa").getValue().toString()) < 4 &&
                                (ds.child("grade").getValue().toString().equals("F") || ds.child("grade").getValue().toString().equals("AB"))) {
                            atkts4.setText("Semester 4 ATKT");
                        }
                        Float sgpa = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        sem4p.setText(String.format("%.1f", sgpa));
                        sem4g.setText(ds.child("grade").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Sem5");
        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        if (Float.parseFloat(ds.child("sgpa").getValue().toString()) < 4 &&
                                (ds.child("grade").getValue().toString().equals("F") || ds.child("grade").getValue().toString().equals("AB"))) {
                            atkts5.setText("Semester 5 ATKT");
                        }
                        Float sgpa = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        sem5p.setText(String.format("%.1f", sgpa));
                        sem5g.setText(ds.child("grade").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentInfo.class);
                startActivity(intent);
            }
        });
    }
}