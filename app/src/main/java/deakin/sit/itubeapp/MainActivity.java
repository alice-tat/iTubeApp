package deakin.sit.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText inputUsername, inputPassword;
    Button loginButton, signUpButton;

    ActivityResultLauncher<Intent> signupActivityResultLauncher;

    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDao = AppDatabase.getInstance(this).userDao();

        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(this::login);
        signUpButton.setOnClickListener(this::signUp);

        signupActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleSignupResult
        );
    }

    private void login(View view) {
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        User existingUser = userDao.login(username, password);
        if (existingUser == null) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("UserID", existingUser.getId());
        intent.putExtra("UserFullname", existingUser.getFullname());
        startActivity(intent);
    }

    private void signUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        signupActivityResultLauncher.launch(intent);
    }

    private void handleSignupResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();
        } else if (result.getResultCode() == RESULT_CANCELED ){
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unknown error occurred", Toast.LENGTH_SHORT).show();
        }
    }
}