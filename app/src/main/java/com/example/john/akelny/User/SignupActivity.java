package com.example.john.akelny;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.john.akelny.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference users;

    EditText userMail, userPassword, userPWConfirm, userPhone, userFN, userLN;
    Button registerBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        userMail = (EditText) findViewById(R.id.userMail);
        userPassword = (EditText) findViewById(R.id.userPW);
        userPWConfirm = (EditText) findViewById(R.id.userPWconfirm);
        userPhone = (EditText) findViewById(R.id.userPhone);
        userFN = (EditText) findViewById(R.id.userFN);
        userLN = (EditText) findViewById(R.id.userLN);

        registerBtn = (Button) findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User  user = new User(userMail.getText().toString(),
                        userPassword.getText().toString(),
                        userPhone.getText().toString(), userFN.getText().toString(), userLN.getText().toString());

                if(userPassword.getText().toString().equals(userPWConfirm.getText().toString()))
                {users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(encodeUserEmail(user.Email)).exists())
                            Toast.makeText(SignupActivity.this, "Email Exists", Toast.LENGTH_LONG).show();
                        else
                        {
                            users.child(encodeUserEmail(user.Email)).setValue(user);
                            Toast.makeText(SignupActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Password isn't Matching!", Toast.LENGTH_SHORT).show();
                }


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
