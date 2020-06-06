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

public class Result extends AppCompatActivity {
    ImageView imageView;
    String uid;
    TextView fullname, batch, grade1, grade2, grade3, grade4, grade5, pointer1, pointer2, pointer3, pointer4, pointer5;
    Button finalres, backtostudentinfo, criteria;
    Float sgpa1 = 0.f, sgpa2 = 0.f, sgpa3 = 0.f, sgpa4 = 0.f, sgpa5 = 0.f, cgpa = 0.f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        imageView = (ImageView) findViewById(R.id.resultimage);
        fullname = (TextView) findViewById(R.id.fullnameresult);
        grade1 = (TextView) findViewById(R.id.g1);
        grade2 = (TextView) findViewById(R.id.g2);
        grade3 = (TextView) findViewById(R.id.g3);
        grade4 = (TextView) findViewById(R.id.g4);
        grade5 = (TextView) findViewById(R.id.g5);
        pointer1 = (TextView) findViewById(R.id.p1);
        pointer2 = (TextView) findViewById(R.id.p2);
        pointer3 = (TextView) findViewById(R.id.p3);
        pointer4 = (TextView) findViewById(R.id.p4);
        pointer5 = (TextView) findViewById(R.id.p5);
        batch = (TextView) findViewById(R.id.batchresult);
        finalres = (Button) findViewById(R.id.finalresult);
        criteria = (Button) findViewById(R.id.resultcriteria);
        backtostudentinfo = (Button) findViewById(R.id.backsi);
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
                                Glide.with(getApplicationContext()).load(uri.toString()).into(imageView);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Couldn't Load Image", Toast.LENGTH_LONG).show();
                            }
                        });
                        fullname.setText(ds.child("firstName").getValue().toString() + " " + ds.child("lastName").getValue().toString());
                        batch.setText(ds.child("batch").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference sem1 = FirebaseDatabase.getInstance().getReference().child("Sem1");
        sem1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        grade1.setText(ds.child("grade").getValue().toString());
                        sgpa1 = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        pointer1.setText(String.format("%.1f", sgpa1));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference sem2 = FirebaseDatabase.getInstance().getReference().child("Sem2");
        sem2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        grade2.setText(ds.child("grade").getValue().toString());
                        sgpa2 = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        pointer2.setText(String.format("%.1f", sgpa2));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference sem3 = FirebaseDatabase.getInstance().getReference().child("Sem3");
        sem3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        grade3.setText(ds.child("grade").getValue().toString());
                        sgpa3 = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        pointer3.setText(String.format("%.1f", sgpa3));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference sem4 = FirebaseDatabase.getInstance().getReference().child("Sem4");
        sem4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        grade4.setText(ds.child("grade").getValue().toString());
                        sgpa4 = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        pointer4.setText(String.format("%.1f", sgpa4));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference sem5 = FirebaseDatabase.getInstance().getReference().child("Sem5");
        sem5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (uid.equals(ds.child("studentid").getValue().toString())) {
                        grade5.setText(ds.child("grade").getValue().toString());
                        sgpa5 = Float.parseFloat(ds.child("sgpa").getValue().toString());
                        pointer5.setText(String.format("%.1f", sgpa5));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        backtostudentinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentInfo.class);
                startActivity(intent);
            }
        });
        finalres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FinalResult.class);
                cgpa = (sgpa1 + sgpa2 + sgpa3 + sgpa4 + sgpa5) / 5.0f;
                intent.putExtra("cgpa", cgpa);
                startActivity(intent);
            }
        });
        criteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FinalResultCriteria.class);
                startActivity(intent);
            }
        });
    }
}