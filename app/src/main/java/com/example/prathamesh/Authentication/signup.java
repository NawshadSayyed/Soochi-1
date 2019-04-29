package com.example.prathamesh.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import MainActivity.MainActivity;
import io.realm.ObjectServerError;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static Extra.Constants.AUTH_URL;


public class signup extends AppCompatActivity implements View.OnClickListener {
    private EditText name, email_id, passwordcheck;
    private FirebaseAuth mAuth;
    private static final String TAG = "";
    private ProgressBar progressBar;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

       /* private void updateUI(FirebaseUser user) {
             hideProgressDialog();
             if (user != null) {
                 mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
                 mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

                 findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                 findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
             } else {
                 mStatusTextView.setText(R.string.signed_out);
                 mDetailTextView.setText(null);

                 findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                 findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
             }
         }
     }*/

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_signup);
        TextView btnSignUp = (TextView) findViewById(R.id.sign_in_button);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, signin.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();

        email_id = (EditText) findViewById(R.id.input_email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        passwordcheck = (EditText) findViewById(R.id.input_password);
        Button ahsignup = (Button) findViewById(R.id.btn_signup);
        ahsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_id.getText().toString();
                String password = passwordcheck.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Eamil Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    //Realm authentication

                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(signup.this, "Registered", Toast.LENGTH_SHORT).show();

                                    SyncCredentials credentials = SyncCredentials.nickname(email, false);
                                    SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
                                        @Override
                                        public void onSuccess(SyncUser user) {
                                            Toast.makeText(signup.this, "Realm Working Yes!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(signup.this, signin.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onError(ObjectServerError error) {
                                            Log.e("Login error", error.toString());
                                        }
                                    });

                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(signup.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                    }
                                   else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(signup.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

            }
        });
    }
     @Override
     public void onClick(View view) {
         //calling register method on click

     }


 }
