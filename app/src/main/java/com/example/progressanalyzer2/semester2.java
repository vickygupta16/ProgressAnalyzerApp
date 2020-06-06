package com.example.progressanalyzer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class semester2 extends AppCompatActivity {
EditText wct,ort,oops2t,ost,awtt,adp,awtp,oops2p,osp,pointer,grade;
Button submit;
HashMap<String,Integer> theoryexam=new HashMap<>();
HashMap<String,Integer> practicalexam=new HashMap<>();
String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester2);

        wct=(EditText)findViewById(R.id.wct);
        ort=(EditText)findViewById(R.id.ort);
        oops2t=(EditText)findViewById(R.id.oops2t);
        ost=(EditText)findViewById(R.id.ost);
        awtt=(EditText)findViewById(R.id.awtt);

        adp=(EditText)findViewById(R.id.adp);
        awtp=(EditText)findViewById(R.id.awtp);
        oops2p=(EditText)findViewById(R.id.oops2p);
        osp=(EditText)findViewById(R.id.osp);

        pointer=(EditText)findViewById(R.id.s2sgpa);
        grade=(EditText)findViewById(R.id.s2grade);

        submit=(Button)findViewById(R.id.sem2button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wct.getText().toString().matches("")&&!ort.getText().toString().matches("")
                &&!oops2t.getText().toString().matches("")&&!ost.getText().toString().matches("")
                &&!awtt.getText().toString().matches("")&&!adp.getText().toString().matches("")
                &&!awtp.getText().toString().matches("")&&!oops2p.getText().toString().matches("")
                &&!osp.getText().toString().matches("")&&!pointer.getText().toString().matches("")
                &&!grade.getText().toString().matches(""))
                {
                    theoryexam.clear();
                    theoryexam.put("Wireless Communication",Integer.parseInt(wct.getText().toString()));
                    theoryexam.put("Operational Research",Integer.parseInt(ort.getText().toString()));
                    theoryexam.put("OOPs2",Integer.parseInt(oops2t.getText().toString()));
                    theoryexam.put("Operating System",Integer.parseInt(ost.getText().toString()));
                    theoryexam.put("Advanced Web Technology",Integer.parseInt(awtt.getText().toString()));
                    practicalexam.clear();
                    practicalexam.put("Android Development",Integer.parseInt(adp.getText().toString()));
                    practicalexam.put("Advanced Web Technology",Integer.parseInt(awtp.getText().toString()));
                    practicalexam.put("OOPs2",Integer.parseInt(oops2p.getText().toString()));
                    practicalexam.put("Operating System",Integer.parseInt(osp.getText().toString()));
                    uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Sem2 sem2=new Sem2();
                    sem2.setTheory(theoryexam);
                    sem2.setPractical(practicalexam);
                    sem2.setGrade(grade.getText().toString());
                    sem2.setSGPA(Float.parseFloat(pointer.getText().toString().trim()));
                    sem2.setStudentid(uid);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Sem2");
                    databaseReference.push().setValue(sem2).addOnCompleteListener(new OnCompleteListener<Void>() {
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