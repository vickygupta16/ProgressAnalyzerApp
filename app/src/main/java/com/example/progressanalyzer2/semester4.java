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

public class semester4 extends AppCompatActivity {
EditText ipt,bit,dnt,stt,bat,ipp,bip,dnp,stp,pointer,grade;
Button submit;
HashMap<String,Integer> theoryexam=new HashMap<>();
HashMap<String,Integer> practicalexam=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester4);
        ipt=(EditText)findViewById(R.id.ipt);
        bit=(EditText)findViewById(R.id.bit);
        dnt=(EditText)findViewById(R.id.dnt);
        stt=(EditText)findViewById(R.id.stt);
        bat=(EditText)findViewById(R.id.bat);
        ipp=(EditText)findViewById(R.id.ipp);
        bip=(EditText)findViewById(R.id.bip);
        dnp=(EditText)findViewById(R.id.dnp);
        stp=(EditText)findViewById(R.id.stp);
        pointer=(EditText)findViewById(R.id.s4sgpa);
        grade=(EditText)findViewById(R.id.s4grade);
        submit=(Button)findViewById(R.id.sem4button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ipt.getText().toString().matches("")&&!bit.getText().toString().matches("")
                &&!dnt.getText().toString().matches("")&&!stt.getText().toString().matches("")
                &&!bat.getText().toString().matches("")&&!ipp.getText().toString().matches("")
                &&!bip.getText().toString().matches("")&&!dnp.getText().toString().matches("")
                &&!stp.getText().toString().matches("")&&!pointer.getText().toString().matches("")
                &&!grade.getText().toString().matches(""))
                {
                    theoryexam.clear();
                    theoryexam.put("Image Processing",Integer.parseInt(ipt.getText().toString()));
                    theoryexam.put("Business Intelligence",Integer.parseInt(bit.getText().toString()));
                    theoryexam.put("DotNet",Integer.parseInt(dnt.getText().toString()));
                    theoryexam.put("Software Testing",Integer.parseInt(stt.getText().toString()));
                    theoryexam.put("Business Analysis",Integer.parseInt(bat.getText().toString()));
                    practicalexam.clear();
                    practicalexam.put("Image Processing",Integer.parseInt(ipp.getText().toString()));
                    practicalexam.put("Business Intelligence",Integer.parseInt(bip.getText().toString()));
                    practicalexam.put("DotNet",Integer.parseInt(dnp.getText().toString()));
                    practicalexam.put("Software Testing",Integer.parseInt(stp.getText().toString()));
                    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Sem4 sem4=new Sem4();
                    sem4.setPractical(practicalexam);
                    sem4.setTheory(theoryexam);
                    sem4.setGrade(grade.getText().toString());
                    sem4.setSGPA(Float.parseFloat(pointer.getText().toString()));
                    sem4.setStudentid(uid);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Sem4");
                    databaseReference.push().setValue(sem4).addOnCompleteListener(new OnCompleteListener<Void>() {
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