package com.example.firsttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private TextView register,forgotpassword;
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private ProgressBar prog;
    public String test;
    private CheckBox profcheck, etudCheck,adminCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register =  findViewById(R.id.gotoRegister);
        register.setOnClickListener(this);

        editTextEmail= findViewById(R.id.inputEmail);
        editTextEmail.setOnClickListener(this);
        editTextPassword=findViewById(R.id.inputPassword);
        editTextPassword.setOnClickListener(this);
        signIn=findViewById(R.id.btnLogin);
        signIn.setOnClickListener(this);

        profcheck=(CheckBox)findViewById(R.id.prof);
        etudCheck=(CheckBox)findViewById(R.id.etud);
        adminCheck=(CheckBox)findViewById(R.id.admin);

        forgotpassword=findViewById(R.id.forgotPassword);
        forgotpassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.gotoRegister:
                startActivity(new Intent(this,Register.class));
                break;
            case R.id.btnLogin:
                userLogin();break;
            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }

    }
    private void userLogin(){
        LinkedList<CheckBox> button = new LinkedList<>();


       boolean checkedProf = profcheck.isChecked();
       boolean checkedEtud = etudCheck.isChecked();
       boolean checkedAdmin= adminCheck.isChecked();
       button.add(profcheck);
       button.add(etudCheck);
       button.add(adminCheck);
        String email =editTextEmail.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user or professor profile
                 if(!(checkedProf || checkedEtud || checkedAdmin)){
                        // No radio buttons are checked
                        Message.message(getApplicationContext(), "Please select a profile.");
                    }
                  else {

                      //verifie the email
                      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                     if(user.isEmailVerified()) {

                      for(CheckBox item : button){
                          if(item.isChecked()){
                              test= item.getText().toString();

                          }
                      }

                      /* if(checkedProf && !checkedAdmin && !checkedEtud){
                           startActivity(new  Intent(Login.this, DashboardProf.class));
                       }
                       else if(checkedEtud && !checkedAdmin && !checkedProf){
                              startActivity(new  Intent(Login.this, DashboardEtudiant.class));}
                         else{
                              startActivity(new  Intent(Login.this, DashboardAdmin.class));}*/
                         switch(test){
                             case"Etudiant":
                                 startActivity(new  Intent(Login.this, DashboardEtudiant.class));break;
                             case "Professeur" :
                                 startActivity(new  Intent(Login.this, DashboardProf.class));
                                 break;
                             case "Administrateur":
                                startActivity(new  Intent(Login.this, DashboardAdmin.class));
                            break;


                         }


                          }



                         else{ user.sendEmailVerification();
                          Toast.makeText(Login.this, "check your email to verify your account", Toast.LENGTH_LONG).show();}


                  }
                  }else {
                          Toast.makeText(Login.this, "Failed to login ! try again!", Toast.LENGTH_LONG).show();


                      }}
        });
        }

}