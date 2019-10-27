package com.example.chatfriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActitvity extends AppCompatActivity {

public static final String TAG = "RegisterActitvity";
    EditText email,username,password;
    Button submit;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    Toolbar toolbar;
  private   ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actitvity);

        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.reg_email);
        username = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_password);
        submit = findViewById(R.id.submit);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

progressDialog = new ProgressDialog(this);


   submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String str_email = email.getText().toString();
        String str_username = username.getText().toString();
        String str_password = password.getText().toString();


if (!TextUtils.isEmpty(str_email) || !TextUtils.isEmpty(str_username) || !TextUtils.isEmpty(str_password))


        progressDialog.setTitle("Registering User");
        progressDialog.setMessage("please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

           register(str_email,str_username,str_password);

    }
});




    }
    private void register(final String email, final String username, final String password){


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {



                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String userid = user.getUid();


                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("email", email);
                            hashMap.put("username", username);
                            hashMap.put("password", password);
                            hashMap.put("imageURL", "default");



                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Intent intent = new Intent(RegisterActitvity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                        progressDialog.dismiss();

                                    }
                                }
                            });

                        }
                       else if(!task.isSuccessful()) {

                                    progressDialog.hide();
                            // If sign in fails, display a message to the user.
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthException e){

                                String error = e.getMessage();

                                Toast.makeText(RegisterActitvity.this,error,Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {

                            }


                        }
                        }
                        // ...

                });
    }
}
