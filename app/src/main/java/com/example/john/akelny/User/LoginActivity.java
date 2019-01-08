package com.example.john.akelny.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.john.akelny.Model.User;

import com.example.john.akelny.R;
import com.example.john.akelny.SignupActivity;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {
    boolean flag = false;
    FirebaseDatabase db;
    DatabaseReference users;
    EditText editUserMail, editUserPassword;
    Button btnSignIn, btnSignup;
    ProgressDialog progressDialog;
    String UserType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        editUserMail = (EditText) findViewById(R.id.editUserMail);
        editUserPassword = (EditText) findViewById(R.id.editUserPW);
        progressDialog = new ProgressDialog(this);
        btnSignIn = (Button) findViewById(R.id.btnLogin);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Loading Login");
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.show();
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
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    if (encodeUserEmail(user.Email).equals(userMail) && user.Password.equals(userPW)) {
                        flag = true;
                        UserType = user.UserType;

                    }
                }
                if (flag == true) {
                    if (UserType.equals("1")) {
                        Intent intent = new Intent(LoginActivity.this, RestrauntsActivity.class);
                        progressDialog.dismiss();
                        startActivity(intent);
                    }
                }

                else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password !", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        flag = false;
    }
}
