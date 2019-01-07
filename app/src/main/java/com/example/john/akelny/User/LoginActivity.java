package com.example.john.akelny;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.john.akelny.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference users;
    EditText editUserMail, editUserPassword;
    Button btnSignIn, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ProgressBar progressBar;
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        editUserMail = (EditText) findViewById(R.id.editUserMail);
        editUserPassword = (EditText) findViewById(R.id.editUserPW);

        btnSignIn = (Button) findViewById(R.id.btnLogin);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(encodeUserEmail(editUserMail.getText().toString()), editUserPassword.getText().toString());
            }
        });

        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }
    public void signIn(final String userMail, final String userPW) {
        if (TextUtils.isEmpty(userMail)) {
            Toast.makeText(getApplicationContext(), "Enter Email Address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPW)) {
            Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
//        users.child("Email").child(userMail).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child(userMail).exists())
//                {
//                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//
//                }
//                else
//                {
//                    Toast.makeText(LoginActivity.this, "Email doesn't Exist", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(user.Email.equals(userMail) && user.Password.equals(userPW)){
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                    else {

                        Toast.makeText(LoginActivity.this, "Email doesn't Exist", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }
}