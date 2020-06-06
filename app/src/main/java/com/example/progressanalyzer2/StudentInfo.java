package com.example.progressanalyzer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.squareup.picasso.Picasso;

import java.net.URL;

public class StudentInfo extends AppCompatActivity {
    TextView fullName, emailid, password, mobile, gender, age, batch;
    String uid;
    Button signout, sem1, sem2, sem3, sem4, sem5, progress, updateprofile, delsem, res;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        sem1 = (Button) findViewById(R.id.sem1button);
        sem2 = (Button) findViewById(R.id.sem2button);
        sem3 = (Button) findViewById(R.id.sem3button);
        sem4 = (Button) findViewById(R.id.sem4button);
        sem5 = (Button) findViewById(R.id.sem5button);
        progress = (Button) findViewById(R.id.viewprogressbutton);
        fullName = (TextView) findViewById(R.id.fullnamevalue);
        emailid = (TextView) findViewById(R.id.emailvalue);
        password = (TextView) findViewById(R.id.passwordvalue);
        mobile = (TextView) findViewById(R.id.mobilevalue);
        gender = (TextView) findViewById(R.id.gendervalue);
        age = (TextView) findViewById(R.id.agevalue);
        batch = (TextView) findViewById(R.id.batchvalue);
        signout = (Button) findViewById(R.id.usignout);
        updateprofile = (Button) findViewById(R.id.updateactivity);
        delsem = (Button) findViewById(R.id.delactivity);
        res = (Button) findViewById(R.id.resultbutton);
        imageView = (ImageView) findViewById(R.id.studentimage);
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
                        //Toast.makeText(getApplicationContext(),""+link,Toast.LENGTH_LONG).show();
                        fullName.setText(ds.child("firstName").getValue().toString() + " " + ds.child("lastName").getValue().toString());
                        mobile.setText(ds.child("mobile").getValue().toString());
                        gender.setText(ds.child("gender").getValue().toString());
                        age.setText(ds.child("age").getValue().toString());
                        batch.setText(ds.child("batch").getValue().toString());
                        emailid.setText(ds.child("emailID").getValue().toString());
                        password.setText(ds.child("password").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        sem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), semester1.class);
                startActivity(intent);
            }
        });
        sem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), semester2.class);
                startActivity(intent);
            }
        });
        sem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), semester3.class);
                startActivity(intent);
            }
        });
        sem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), semester4.class);
                startActivity(intent);
            }
        });
        sem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), semester5.class);
                startActivity(intent);
            }
        });
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserProgress.class);
                startActivity(intent);
            }
        });
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Update.class);
                startActivity(intent);
            }
        });
        delsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeleteSemester.class);
                startActivity(intent);
            }
        });
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Result.class);
                startActivity(intent);
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
