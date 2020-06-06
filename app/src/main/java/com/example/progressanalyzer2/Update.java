package com.example.progressanalyzer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {
EditText fn,ln,mob,ageu;
RadioButton gender,batch;
RadioGroup gendergroup,batchgroup;
Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        fn=(EditText)findViewById(R.id.fnu);
        ln=(EditText)findViewById(R.id.lnu);
        mob=(EditText)findViewById(R.id.mobileu);
        ageu=(EditText)findViewById(R.id.ageu);
        update=(Button)findViewById(R.id.updatebutton);
        gendergroup=(RadioGroup)findViewById(R.id.genderu);
        batchgroup=(RadioGroup)findViewById(R.id.batchu);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String first_name=fn.getText().toString();
                final String last_name=ln.getText().toString();
                final String mobile=mob.getText().toString();
                final String age=ageu.getText().toString();
                int gid=gendergroup.getCheckedRadioButtonId();
                gender=findViewById(gid);
                int bid=batchgroup.getCheckedRadioButtonId();
                batch=findViewById(bid);
                if(!first_name.equals("")&&!last_name.equals("")&&!mobile.equals("")&&!age.equals("")&&gender!=null&&batch!=null
                &&(mobile.length()>=1&&mobile.length()<=10)
                &&(Integer.parseInt(age)>=19&&Integer.parseInt(age)<=25))
                {
                    final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("user");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren())
                            {
                                if(ds.child("userid").getValue().toString().equals(uid))
                                {
                                    ds.child("firstName").getRef().setValue(first_name);
                                    ds.child("lastName").getRef().setValue(last_name);
                                    ds.child("mobile").getRef().setValue(mobile);
                                    ds.child("age").getRef().setValue(age);
                                    ds.child("gender").getRef().setValue(gender.getText().toString());
                                    ds.child("batch").getRef().setValue(batch.getText().toString());
                                    StringBuffer stringBuffer=new StringBuffer();
                                    stringBuffer.append("Profile Details Updated");
                                    showMessage(stringBuffer.toString(),"Update Success");
                                    break;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            StringBuffer stringBuffer=new StringBuffer();
                            stringBuffer.append(databaseError.getDetails());
                            showMessage(stringBuffer.toString(),"Update Unsuccessful");
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
