package com.example.chatfriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email,pasword;
    Button Login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    public static final String TAG = "LoginActitvity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.log_email);
        pasword = findViewById(R.id.log_pass);
        Login = findViewById(R.id.login);

        Toolbar toolbar = new Toolbar(this);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressDialog = new ProgressDialog(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = email.getText().toString();
                String str_pasword = pasword.getText().toString();

                if (!TextUtils.isEmpty(str_email) || !TextUtils.isEmpty(str_pasword)){

                    progressDialog.setTitle("Signing In");
                    progressDialog.setMessage("please wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    Login(str_email,str_pasword);

                }
            }
        });


    }

    private void Login(String email, String password){

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                            progressDialog.dismiss();

                        } else if (!task.isSuccessful()){

                                progressDialog.hide();

                            try{
                                throw task.getException();
                            }catch (FirebaseAuthException e){

                                String error = e.getMessage();

                                Toast.makeText(LoginActivity.this,error,Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {

                            }
                        }

                        // ...
                    }
                });


    }
}
