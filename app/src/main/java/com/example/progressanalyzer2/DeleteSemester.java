package com.example.progressanalyzer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteSemester extends AppCompatActivity {
RadioGroup semgroup;
RadioButton sem;
Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_semester);
        semgroup=(RadioGroup)findViewById(R.id.delsemgroup);
        delete=(Button)findViewById(R.id.deletebutton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int semid=semgroup.getCheckedRadioButtonId();
                sem=findViewById(semid);
                DatabaseReference databaseReference;
                if(sem.getText().toString().equals("Semester 1"))
                {
                    deleteSem("Sem1");
                }
                else if(sem.getText().toString().equals("Semester 2"))
                {
                    deleteSem("Sem2");
                }
                else if(sem.getText().toString().equals("Semester 3"))
                {
                    deleteSem("Sem3");
                }
                else if(sem.getText().toString().equals("Semester 4"))
                {
                    deleteSem("Sem4");
                }
                else if(sem.getText().toString().equals("Semester 5"))
                {
                    deleteSem("Sem5");
                }
                semgroup.clearCheck();
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
    public void deleteSem(String semname)
    {
        final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child(semname);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if(uid.equals(ds.child("studentid").getValue().toString()))
                    {
                        ds.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    StringBuffer stringBuffer=new StringBuffer();
                                    stringBuffer.append(sem.getText().toString()+" Marks Record Deleted Successfully");
                                    showMessage(stringBuffer.toString(),"Delete Success");
                                }
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                StringBuffer stringBuffer=new StringBuffer();
                stringBuffer.append(databaseError.getDetails());
                showMessage(stringBuffer.toString(),"Error");
            }
        });
    }
}