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

public class semester3 extends AppCompatActivity {
EditText pyt,set,ismt,mpapt,est,pyp,rmp,uxdp,ooadp,pointer,grade;
Button submit;
HashMap<String,Integer> theoryexam=new HashMap<>();
HashMap<String,Integer> practicalexam=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester3);
        pyt=(EditText)findViewById(R.id.pyt);
        set=(EditText)findViewById(R.id.set);
        ismt=(EditText)findViewById(R.id.ismt);
        mpapt=(EditText)findViewById(R.id.mpapt);
        est=(EditText)findViewById(R.id.est);
        pyp=(EditText)findViewById(R.id.pyp);
        rmp=(EditText)findViewById(R.id.rmp);
        uxdp=(EditText)findViewById(R.id.uxdp);
        ooadp=(EditText)findViewById(R.id.ooadp);
        pointer=(EditText)findViewById(R.id.s3sgpa);
        grade=(EditText)findViewById(R.id.s3grade);
        submit=(Button)findViewById(R.id.sem3button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pyt.getText().toString().matches("")&&!set.getText().toString().matches("")
                        &&!ismt.getText().toString().matches("")&&!mpapt.getText().toString().matches("")
                        &&!est.getText().toString().matches("")&&!pyp.getText().toString().matches("")
                        &&!rmp.getText().toString().matches("")&&!uxdp.getText().toString().matches("")
                        &&!ooadp.getText().toString().matches("")&&!pointer.getText().toString().matches("")
                        &&!grade.getText().toString().matches(""))
                {
                    theoryexam.clear();
                    theoryexam.put("Python",Integer.parseInt(pyt.getText().toString()));
                    theoryexam.put("Software Engineering",Integer.parseInt(set.getText().toString()));
                    theoryexam.put("Information Security Management",Integer.parseInt(ismt.getText().toString()));
                    theoryexam.put("Management Processes and Practices",Integer.parseInt(mpapt.getText().toString()));
                    theoryexam.put("Enterprise System",Integer.parseInt(est.getText().toString()));
                    practicalexam.clear();
                    practicalexam.put("Python",Integer.parseInt(pyp.getText().toString()));
                    practicalexam.put("Research Methodology",Integer.parseInt(rmp.getText().toString()));
                    practicalexam.put("UX Design",Integer.parseInt(uxdp.getText().toString()));
                    practicalexam.put("Object Oriented Modeling & Design",Integer.parseInt(ooadp.getText().toString()));
                    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Sem3 sem3=new Sem3();
                    sem3.setTheory(theoryexam);
                    sem3.setPractical(practicalexam);
                    sem3.setSGPA(Float.parseFloat(pointer.getText().toString()));
                    sem3.setGrade(grade.getText().toString());
                    sem3.setStudentid(uid);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Sem3");
                    databaseReference.push().setValue(sem3).addOnCompleteListener(new OnCompleteListener<Void>() {
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