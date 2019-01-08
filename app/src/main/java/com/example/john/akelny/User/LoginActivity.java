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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class LoginActivity extends Activity {
    boolean flag = false;
    FirebaseDatabase db;
    DatabaseReference users;
    EditText editUserMail, editUserPassword;
    Button btnSignIn, btnSignup;
    ProgressDialog progressDialog;
    String UserType;
    LoginButton fbLogin;
    CallbackManager cbm;
    FirebaseAuth auth;

    public static boolean isAppRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        fbLogin = (LoginButton)findViewById(R.id.login_button);
        cbm = CallbackManager.Factory.create();
        fbLogin.setReadPermissions(Arrays.asList("email"));
        auth = FirebaseAuth.getInstance();
        AppEventsLogger.activateApp(this);
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





    public void buttonOnClickFBLogin(View v)
    {
        LoginManager.getInstance().registerCallback(cbm, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
                startActivity(new Intent(LoginActivity.this, RestrauntsActivity.class));
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "User Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential cred = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(cred).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = auth.getCurrentUser();
                    editUserMail.setText(user.getEmail());
                }else
                {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        cbm.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        isAppRunning = false;
        //dialog.dismiss();
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
