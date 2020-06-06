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
import java.util.Map;

public class semester1 extends AppCompatActivity {
EditText wtt,oopst,cnt,dbmst,mathst,wtp,oopsp,cnp,dbmsp,pointer,grade;
Button submit;
HashMap<String,Integer> theoryexam=new HashMap<>();
HashMap<String,Integer> practicalexam=new HashMap<>();
String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester1);

        wtt=(EditText)findViewById(R.id.wtt);
        oopst=(EditText)findViewById(R.id.oopst);
        cnt=(EditText)findViewById(R.id.cnt);
        dbmst=(EditText)findViewById(R.id.dbmst);
        mathst=(EditText)findViewById(R.id.dmt);

        wtp=(EditText)findViewById(R.id.wtp);
        oopsp=(EditText)findViewById(R.id.oopsp);
        cnp=(EditText)findViewById(R.id.cnp);
        dbmsp=(EditText)findViewById(R.id.dbmsp);

        pointer=(EditText)findViewById(R.id.s1sgpa);
        grade=(EditText)findViewById(R.id.s1grade);

        submit=(Button)findViewById(R.id.sem1button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wtt.getText().toString().matches("")&&!oopst.getText().toString().matches("")
                &&!cnt.getText().toString().matches("")&&!dbmst.getText().toString().matches("")
                &&!mathst.getText().toString().matches("")&&!wtp.getText().toString().matches("")
                &&!oopsp.getText().toString().matches("")&&!cnp.getText().toString().matches("")
                &&!dbmsp.getText().toString().matches("")&&!pointer.getText().toString().matches("")
                &&!grade.getText().toString().matches(""))
                {
                    theoryexam.clear();
                    theoryexam.put("Web Technology",Integer.parseInt(wtt.getText().toString()));
                    theoryexam.put("OOPs",Integer.parseInt(oopst.getText().toString()));
                    theoryexam.put("Computer Networks",Integer.parseInt(cnt.getText().toString()));
                    theoryexam.put("DBMS",Integer.parseInt(dbmst.getText().toString()));
                    theoryexam.put("Maths & Stats",Integer.parseInt(mathst.getText().toString()));
                    practicalexam.clear();
                    practicalexam.put("Web Technology",Integer.parseInt(wtp.getText().toString()));
                    practicalexam.put("OOPs",Integer.parseInt(oopsp.getText().toString()));
                    practicalexam.put("Computer Networks",Integer.parseInt(cnp.getText().toString()));
                    practicalexam.put("DBMS",Integer.parseInt(dbmsp.getText().toString()));
                    uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Sem1 sem1=new Sem1();
                    sem1.setTheory(theoryexam);
                    sem1.setPractical(practicalexam);
                    sem1.setSGPA(Float.parseFloat(pointer.getText().toString()));
                    sem1.setGrade(grade.getText().toString());
                    sem1.setStudentid(uid);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Sem1");
                    databaseReference.push().setValue(sem1).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                    /*for(Map.Entry<String,Integer> entry:theoryexam.entrySet())
                    {
                        stringBuffer.append(entry.getKey()+"\t"+entry.getValue()+"\n");
                    }
                    for(Map.Entry<String,Integer> entry:practicalexam.entrySet())
                    {
                        stringBuffer.append(entry.getKey()+"\t"+entry.getValue()+"\n");
                    }
                    stringBuffer.append(pointer.getText().toString()+"\n"+grade.getText().toString());*/
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
