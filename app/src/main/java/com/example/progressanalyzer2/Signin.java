package com.example.progressanalyzer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
EditText mail,pass;
TextView signuptv;
Button signin;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        signuptv=(TextView)findViewById(R.id.redirectsignup);
        mail=(EditText)findViewById(R.id.umail);
        pass=(EditText)findViewById(R.id.upass);
        signin=(Button)findViewById(R.id.usignin);
        firebaseAuth=FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid=mail.getText().toString();
                String password=pass.getText().toString();
                if(!emailid.equals("")&&!password.equals(""))
                {
                    firebaseAuth.signInWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent=new Intent(getApplicationContext(),StudentInfo.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                StringBuffer stringBuffer=new StringBuffer();
                                stringBuffer.append(task.getException().toString());
                                showMessage(stringBuffer.toString(),"Error Signing In");
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
        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
            }
        });
    }
    public void showMessage(String msg,String title)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.show();
    }
}
