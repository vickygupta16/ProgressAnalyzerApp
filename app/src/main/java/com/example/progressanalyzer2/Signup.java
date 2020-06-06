package com.example.progressanalyzer2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Signup extends AppCompatActivity {
    EditText firstName, lastName, emailid, password, contact, age;
    RadioButton gender, batch;
    RadioGroup gendergroup, batchgroup;
    Button createacc, chooseimage;
    TextView signintv;
    FirebaseAuth firebaseAuth;
    String uid;
    private static final int PICK_IAMGE_REQUEST = 1;
    private ImageView suimageView;
    private ProgressBar suprogress;
    private Uri imageUri;
    private StorageReference storageReference;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
    ;
    UserData userData = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signintv = (TextView) findViewById(R.id.redirectsignin);
        firstName = (EditText) findViewById(R.id.fn);
        lastName = (EditText) findViewById(R.id.ln);
        emailid = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.pw);
        contact = (EditText) findViewById(R.id.numb);
        age = (EditText) findViewById(R.id.age);
        gendergroup = (RadioGroup) findViewById(R.id.genderradiogroup);
        batchgroup = (RadioGroup) findViewById(R.id.batchradiogroup);
        chooseimage = (Button) findViewById(R.id.selectimgbtn);
        suimageView = (ImageView) findViewById(R.id.suimage);
        suprogress = (ProgressBar) findViewById(R.id.progress);
        firebaseAuth = FirebaseAuth.getInstance();
        createacc = (Button) findViewById(R.id.ca);
        suimageView = (ImageView) findViewById(R.id.suimage);
        storageReference = FirebaseStorage.getInstance().getReference("user");
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String first_name = firstName.getText().toString();
                final String last_name = lastName.getText().toString();
                final String email_id = emailid.getText().toString();
                final String pass = password.getText().toString();
                int bid = batchgroup.getCheckedRadioButtonId();
                batch = findViewById(bid);
                final String mobile = contact.getText().toString();
                final String uage = age.getText().toString();
                int gid = gendergroup.getCheckedRadioButtonId();
                gender = findViewById(gid);
                if (!first_name.equals("") && !last_name.equals("") && !email_id.equals("") && !pass.equals("") &&
                        batch != null && !mobile.equals("") && !pass.equals("") && gender != null) {
                    firebaseAuth.createUserWithEmailAndPassword(email_id, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                uid = firebaseAuth.getCurrentUser().getUid();
                                userData.setUserid(uid);
                                userData.setFirstName(first_name);
                                userData.setLastName(last_name);
                                userData.setEmailID(email_id);
                                userData.setPassword(pass);
                                userData.setBatch(batch.getText().toString());
                                userData.setMobile(mobile);
                                userData.setAge(uage);
                                userData.setGender(gender.getText().toString());
                                uploadFile(email_id);
                                firstName.setText("");
                                lastName.setText("");
                                emailid.setText("");
                                password.setText("");
                                batchgroup.clearCheck();
                                contact.setText("");
                                age.setText("");
                                gendergroup.clearCheck();
                                createacc.setEnabled(false);
                                chooseimage.setEnabled(false);
                            }
                        }
                    });
                } else {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Kindly fill up all the fields correctly");
                    showMessage(stringBuffer.toString(), "Fields Missing");
                }
            }
        });

        signintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signin.class);
                startActivity(intent);
            }
        });
    }

    public void showMessage(String msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.show();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IAMGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IAMGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(suimageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile(String email) {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(email + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    suprogress.setProgress(0);
                                }
                            }, 500);
                            //Toast.makeText(getApplicationContext(),""+getFileExtension(imageUri),Toast.LENGTH_LONG).show();
                            userData.setImageExtension(getFileExtension(imageUri));
                            userData.setImageUrl(taskSnapshot.getStorage().getDownloadUrl().toString());
                            databaseReference.push().setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        StringBuffer stringBuffer = new StringBuffer();
                                        stringBuffer.append("You have successfully created your account\n"
                                                + "Please Signin with your credentials");
                                        showMessage(stringBuffer.toString(), "Sign Up Successful");
                                    } else {
                                        StringBuffer stringBuffer = new StringBuffer();
                                        stringBuffer.append(task.getException());
                                        showMessage(stringBuffer.toString(), "Error Signing Up");
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(e.getMessage());
                            showMessage(stringBuffer.toString(), "Error");
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            suprogress.setProgress((int) progress);
                        }
                    });
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No File Selected");
            showMessage(stringBuffer.toString(), "Profile Image Error");
        }
    }
}