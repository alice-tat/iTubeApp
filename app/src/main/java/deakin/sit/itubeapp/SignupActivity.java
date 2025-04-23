package deakin.sit.itubeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {
    EditText inputFullname, inputUsername, inputPassword, inputConfirmPassword;
    Button backButton, createAccountButton;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDao = AppDatabase.getInstance(this).userDao();

        inputFullname = findViewById(R.id.inputFullname);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);

        backButton = findViewById(R.id.backButton);
        createAccountButton = findViewById(R.id.createAccountButton);

        backButton.setOnClickListener(this::backToLogin);
        createAccountButton.setOnClickListener(this::createAccount);
    }

    private void backToLogin(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void createAccount(View view) {
        String fullname = inputFullname.getText().toString();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!confirmPassword.equals(password)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        User existingUser = userDao.getUserByUsername(username);
        if (existingUser != null) {
            Toast.makeText(this, "Username already existed", Toast.LENGTH_SHORT).show();
            return;
        }

        userDao.register(new User(fullname, username, password));

        setResult(RESULT_OK);
        finish();
    }
}