package com.example.mynewsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class FragmentSignIn extends Fragment {

    EditText e_si_email, e_si_password;
    Button btn_signin;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        e_si_email = view.findViewById(R.id.edt_email);
        e_si_password = view.findViewById(R.id.edt_password);
        btn_signin = view.findViewById(R.id.btn_signin);
        progressBar = view.findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });

        return view;

    }

    private void signin() {
        String string_password = e_si_password.getText().toString().trim();
        String string_email  = e_si_email.getText().toString().trim();

        if(string_email.isEmpty())
        {
            e_si_email.setError("Email id is required!");
            e_si_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(string_email).matches()){
            e_si_email.setError("Enter valid s_email!");
            e_si_email.requestFocus();
            return;
        }

        if(string_password.isEmpty()){
            e_si_password.setError("Please enter your password!");
            e_si_password.requestFocus();
            return;
        }
        if(string_password.length() < 8){
            e_si_password.setError("Enter At least 8 Character password!");
            e_si_password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(string_email,string_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(getActivity().getApplication(),home.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(getActivity().getApplication(), "Failed to login", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}