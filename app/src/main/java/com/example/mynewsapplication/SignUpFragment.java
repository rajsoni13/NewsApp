package com.example.mynewsapplication;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    TextView signin;
    EditText e_name, e_phone_number, e_emailid, e_password;
    Button signUpBtn;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signin = v.findViewById(R.id.gotosignin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSignIn fragmentSignIn = new FragmentSignIn();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.newcard,fragmentSignIn);
                transaction.commit();
            }
        });
        e_name = v.findViewById(R.id.name);
        e_emailid = v.findViewById(R.id.emailid);
        e_phone_number = v.findViewById(R.id.phone_number);
        e_password = v.findViewById(R.id.password);
        progressBar = v.findViewById(R.id.progressbar_r);
        signUpBtn = v.findViewById(R.id.signUpBtn);
        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registeruser();
            }
        });

        return v;
    }

    private void Registeruser() {
        String s_email = e_emailid.getText().toString().trim();
        String s_passwords = e_password.getText().toString().trim();
        String s_phone = e_phone_number.getText().toString().trim();
        String s_name = e_name.getText().toString().trim();

        if(s_name.isEmpty())
        {
            e_name.setError("Name is required!");
            e_name.requestFocus();
            return;
        }

        if(s_email.isEmpty())
        {
            e_emailid.setError("Email id is required!");
            e_emailid.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(s_email).matches()){
            e_emailid.setError("Enter valid s_email!");
            e_emailid.requestFocus();
            return;
        }
        if(s_phone.isEmpty()){
            e_phone_number.setError("Enter Phone Number");
            e_phone_number.requestFocus();
            return;
        }
        if(s_phone.length() < 10){
            e_phone_number.setError("Enter 10 digit Number!");
            e_phone_number.requestFocus();
            return;
        }
        if(s_passwords.isEmpty())
        {
            e_password.setError("Password is required!");
            e_password.requestFocus();
            return;
        }
        if(s_passwords.length() < 8){
            e_password.setError("Enter At least 8 character!");
            e_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(s_email,s_passwords)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(s_name,s_email,s_phone);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity().getApplication(), "User Added!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);


                                    }else {
                                        Toast.makeText(getActivity().getApplication(), "Failed", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(getActivity().getApplication(), "Failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }

    @Override
    public void onClick(View view) {

    }
}