package de.hvv.hackathon.citytour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button loginbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = (EditText)findViewById(R.id.loginEditText);
        editTextPassword = (EditText) findViewById(R.id.passwordEditText);
        loginbutton = (Button) findViewById(R.id.buttonLogin);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextLogin.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Name und Passwort bitte", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Intent intent;
                    if(!preferences.contains("edited"))
                        intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    else
                        intent = new Intent(getApplicationContext(), MainActivityBottomNavigation.class);

                    startActivity(intent);
                }
            }
        });
    }
}
