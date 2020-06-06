package com.example.progressanalyzer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FinalResult extends AppCompatActivity {
    Button backtoresult, home;
    ImageView profile;
    TextView fn, batch, cgpatv, gradetv, gradepointtv;
    String uid;
    Float formatcgpa = 0.f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);
        Bundle bundle = getIntent().getExtras();
        Float CGPA = bundle.getFloat("cgpa");
        fn = (TextView) findViewById(R.id.fullnamefinalresult);
        batch = (TextView) findViewById(R.id.batchfinalresult);
        cgpatv = (TextView) findViewById(R.id.finalcgpa);
        gradetv = (TextView) findViewById(R.id.finalgrade);
        gradepointtv = (TextView) findViewById(R.id.finalgradepoint);
        profile = (ImageView) findViewById(R.id.finalresultimage);
        backtoresult = (Button) findViewById(R.id.backresult);
        home = (Button) findViewById(R.id.backhome);
        formatcgpa = Float.parseFloat(String.format("%.1f", CGPA));
        cgpatv.setText(formatcgpa.toString());
        if (formatcgpa <= 10 && formatcgpa >= 9.5) {
            gradetv.setText("O");
            gradepointtv.setText("10");
        } else if (formatcgpa <= 9.49 && formatcgpa >= 8.5) {
            gradetv.setText("A+");
            gradepointtv.setText("9");
        } else if (formatcgpa <= 8.49 && formatcgpa >= 7.5) {
            gradetv.setText("A");
            gradepointtv.setText("8");
        } else if (formatcgpa <= 7.49 && formatcgpa >= 6.5) {
            gradetv.setText("B+");
            gradepointtv.setText("7");
        } else if (formatcgpa <= 6.49 && formatcgpa >= 5.5) {
            gradetv.setText("B");
            gradepointtv.setText("6");
        } else if (formatcgpa <= 5.49 && formatcgpa >= 4.5) {
            gradetv.setText("C");
            gradepointtv.setText("5");
        } else if (formatcgpa <= 4.49 && formatcgpa >= 4) {
            gradetv.setText("P");
            gradepointtv.setText("4");
        } else if (formatcgpa < 4) {
            gradetv.setText("F");
            gradepointtv.setText("0");
        }
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("userid").getValue().toString())) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        StorageReference imgRef = storageReference.child("user/" + ds.child("emailID").getValue().toString() +
                                "." + ds.child("imageExtension").getValue().toString());
                        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getApplicationContext()).load(uri.toString()).into(profile);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Couldn't Load Image", Toast.LENGTH_LONG).show();
                            }
                        });
                        fn.setText(ds.child("firstName").getValue().toString() + " " + ds.child("lastName").getValue().toString());
                        batch.setText(ds.child("batch").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        backtoresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Result.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentInfo.class);
                startActivity(intent);
            }
        });
    }
}
