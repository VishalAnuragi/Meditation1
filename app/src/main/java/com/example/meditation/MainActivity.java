package com.example.meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    TextView name , email, email_txt;
    Button logout;
    private String guestname ;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY = "myKey";
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        email_txt = findViewById(R.id.email_txt);

        try {
            GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
            if (signInAccount != null)
            {
                name.setText(signInAccount.getDisplayName());
                email.setText(signInAccount.getEmail());
            }
            else {

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                guestname = sharedPreferences.getString(KEY, "");

                name.setText(guestname);
                email_txt.setVisibility(View.INVISIBLE);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  revokeAccess();
                FirebaseAuth.getInstance().signOut();

                try {
                    SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, 0);
                    preferences.edit().remove(KEY).apply();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }


                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText( MainActivity.this, "Logged out Successfully." , Toast.LENGTH_SHORT).show();

                     //   AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                    }
                });
    }
}