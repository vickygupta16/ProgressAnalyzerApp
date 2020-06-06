package com.example.progressanalyzer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class semester5 extends AppCompatActivity {
EditText adct,mlt,iott,msdt,bdat,adcp,mlp,iotp,msdp,pointer,grade;
Button submit;
HashMap<String,Integer> theoryexam=new HashMap<>();
HashMap<String,Integer> practicalexam=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester5);
        adct=(EditText)findViewById(R.id.adct);
        mlt=(EditText)findViewById(R.id.mlt);
        iott=(EditText)findViewById(R.id.iott);
        msdt=(EditText)findViewById(R.id.msdt);
        bdat=(EditText)findViewById(R.id.bdat);
        adcp=(EditText)findViewById(R.id.adcp);
        mlp=(EditText)findViewById(R.id.mlp);
        iotp=(EditText)findViewById(R.id.iotp);
        msdp=(EditText)findViewById(R.id.msdp);
        pointer=(EditText)findViewById(R.id.s5sgpa);
        grade=(EditText)findViewById(R.id.s5grade);
        submit=(Button)findViewById(R.id.sem5button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!adct.getText().toString().matches("")&&!mlt.getText().toString().matches("")
                &&!iott.getText().toString().matches("")&&!msdt.getText().toString().matches("")
                &&!bdat.getText().toString().matches("")&&!adcp.getText().toString().matches("")
                &&!mlp.getText().toString().matches("")&&!iotp.getText().toString().matches("")
                &&!msdp.getText().toString().matches("")&&!pointer.getText().toString().matches("")
                &&!grade.getText().toString().matches(""))
                {
                    theoryexam.clear();
                    theoryexam.put("Advanced Distributed Computing",Integer.parseInt(adct.getText().toString()));
                    theoryexam.put("Machine Learning",Integer.parseInt(mlt.getText().toString()));
                    theoryexam.put("Internet of Things",Integer.parseInt(iott.getText().toString()));
                    theoryexam.put("Multimedia System Design",Integer.parseInt(msdt.getText().toString()));
                    theoryexam.put("Big Data Analysis",Integer.parseInt(bdat.getText().toString()));
                    practicalexam.clear();
                    practicalexam.put("Advanced Distributed Computing",Integer.parseInt(adcp.getText().toString()));
                    practicalexam.put("Machine Learning",Integer.parseInt(mlp.getText().toString()));
                    practicalexam.put("Internet of Things",Integer.parseInt(iotp.getText().toString()));
                    practicalexam.put("Multimedia System Design",Integer.parseInt(msdp.getText().toString()));
                    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Sem5 sem5=new Sem5();
                    sem5.setTheory(theoryexam);
                    sem5.setPractical(practicalexam);
                    sem5.setGrade(grade.getText().toString());
                    sem5.setSGPA(Float.parseFloat(pointer.getText().toString()));
                    sem5.setStudentid(uid);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Sem5");
                    databaseReference.push().setValue(sem5).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                StringBuffer stringBuffer=new StringBuffer();
                                stringBuffer.append("Marks successfully recorded");
                                showMessage(stringBuffer.toString(),"Success");
                            }
                            else
                            {
                                StringBuffer stringBuffer=new StringBuffer();
                                stringBuffer.append("Marks recording unsuccessful");
                                showMessage(stringBuffer.toString(),"Error");
                            }
                        }
                    });
                }
                else
                {
                    StringBuffer stringBuffer=new StringBuffer();
                    stringBuffer.append("Kindly fill up all the fields correctly");
                    showMessage(stringBuffer.toString(),"Fields Missing");
                }
            }
        });
    }
    public void showMessage(String msg,String title)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.show();
    }
}