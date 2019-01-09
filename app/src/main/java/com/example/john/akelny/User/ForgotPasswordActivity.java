package com.example.john.akelny.User;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.john.akelny.R;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends Activity {

    EditText email;
    Button resetBtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = (EditText)findViewById(R.id.emailEdit);
        resetBtn = (Button) findViewById(R.id.btnReset);
        auth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usermail = email.toString().trim();
                if(usermail.equals(""))
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Please specify the E-mail address!", Toast.LENGTH_SHORT).show();
                }else
                {
//                    auth.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(Task<AuthResult> task) {
//                            if(task.isSuccessful())
//                            {
//                                Toast.makeText(ForgotPasswordActivity.this, "Password reset e-mail sent!", Toast.LENGTH_SHORT).show();
//                                finish();
//                                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
//                            }else
//                            {
//                                Toast.makeText(ForgotPasswordActivity.this, "Error Sending Email!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }
            }
        });


    }
}
