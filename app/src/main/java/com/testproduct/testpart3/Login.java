package com.testproduct.testpart3;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //if(firebaseAuth.getCurrentUser() != null){
            //transfer to another activity
            //finish();
            //startActivity(new Intent(getApplicationContext(), Login.class));


        //}

        progressDialog = new ProgressDialog(this);

        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewSignUp = (TextView) findViewById(R.id.textRegister);

        buttonLogIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogIn){
            userLogin();
        }

        if (view == textViewSignUp){
            startActivity(new Intent(this, Register.class));
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        final EditText editTextPrompt = new EditText(this);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            //prevents further execution
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            //prevents further execution
            return;
        }

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //if the user is successfully logged on

                            // Prompt Message Upon MainActivty Loading

                            builder.setTitle("Before we start...");
                            builder.setMessage("Please enter your planned budget");
                            builder.setCancelable(false);
                            builder.setView(editTextPrompt);
                            builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(editTextPrompt.getText().toString().isEmpty()) {
                                        Toast.makeText(getApplicationContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
                                        //prevents further execution
                                        return;
                                    }
                                    Intent intent;
                                    Toast.makeText(getApplicationContext(), "Thanks for the input. Now beginning demo.", Toast.LENGTH_LONG).show();
                                    //beginning profile
                                    finish();
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("PromptValue",editTextPrompt.getText().toString());
                                    startActivity(intent);
                                }
                            });
                            builder.show();

                        }
                        else{
                            Toast.makeText(Login.this, "Username or password invalid. Try Again?", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}