package com.app.quizoofinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    SignInButton Google;
    ImageButton Signin;
    TextView Signup;
    TextInputLayout Phonenum, Password;
    String _phonenum, _password;
    ProgressBar pb;
    float v = 0;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CardView logincard = findViewById(R.id.login_card);
        logincard.setBackgroundResource(R.drawable.cardback_login);

        pb = findViewById(R.id.pblogin);
        Signup = findViewById(R.id.signup);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


        /// Social Media Animation //////////////////

        Google = findViewById(R.id.googlelog);
        Google.setSize(SignInButton.SIZE_WIDE);
        Google.setTranslationY(500);
        Google.setAlpha(v);
        Google.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(100).start();


        // Firebase
        Phonenum = (TextInputLayout) findViewById(R.id.Phonenum);
        Password = (TextInputLayout) findViewById(R.id.Password);

        Signin = findViewById(R.id.Signin_button);

        pb.setVisibility(View.GONE);
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Phonenum.getEditText().getText().toString().isEmpty() == false && Password.getEditText().getText().toString().isEmpty() == false) {
                    pb.setVisibility(View.VISIBLE);
                    String _phonenum = Phonenum.getEditText().getText().toString().trim();
                    final String _password = Password.getEditText().getText().toString().trim();
                    if (_phonenum.charAt(0) == '0') {
                        _phonenum = _phonenum.substring(1);
                    }
                    Query checkuser = FirebaseDatabase.getInstance().getReference("user").orderByChild("phone").equalTo(_phonenum);

                    final String final_phonenum = _phonenum;
                    checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String systempass = snapshot.child(final_phonenum).child("password").getValue(String.class);
                                if (systempass.equals(_password)) {
                                    Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                                    intent.putExtra("usernamelogin", snapshot.child(final_phonenum).child("name").getValue(String.class));
                                    startActivity(intent);
                                } else {
                                    pb.setVisibility(View.GONE);
                                    Password.setError("Password doesn't match");
                                }
                            } else {
                                Phonenum.setError("No such user exist!");
                                pb.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //// VALIDATION ON LOGIN //////////////////////////////////////
                else {
                    if (Phonenum.getEditText().getText().toString().isEmpty() == true) {
                        Phonenum.setError("Please Enter Your Phone Number");
                    }
                    if (Password.getEditText().getText().toString().isEmpty() == true) {
                        Password.setError("Please Enter Your Password");
                    }
                    Phonenum.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (Phonenum.getEditText().getText().toString().isEmpty() == true) {
                                Phonenum.setError("Please Enter Your Phone Number");
                            } else {
                                Phonenum.setError(null);
                                Phonenum.setErrorEnabled(false);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    Password.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (Password.getEditText().getText().toString().isEmpty() == true) {
                                Password.setError("Please Enter Your Password");
                            } else {
                                Password.setError(null);
                                Password.setErrorEnabled(false);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }

            }
        });


        // Google Sign In ////////////////////////////////////////////


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.googlelog:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        //////////////////////////////////////////////////
    }


    ////////// Google Functions /////////////////////////////////////////
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            String personName = null;
            if (acct != null) {
                personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
            }
            Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
            intent.putExtra("usernamelogin", personName);
            startActivity(intent);


        } catch (ApiException e) {
            Log.d("Message", e.toString());
        }
    }
}