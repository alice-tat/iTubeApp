package deakin.sit.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;

    private HomeMainFragment homeMainFragment;
    private HomePlayVideoFragment homePlayVideoFragment;
    private HomeMyPlaylistFragment homeMyPlaylistFragment;

    private static UserPlaylistDao userPlaylistDao;

    private static int currentUserID;
    private static String currentUserFullname;

    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userPlaylistDao = AppDatabase.getInstance(this).userPlaylistDao();
        Intent intent = getIntent();
        currentUserID = intent.getIntExtra("UserID", -1);
        currentUserFullname = intent.getStringExtra("UserFullname");

        welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessage.setText("Hi " + currentUserFullname);

        // Manage fragment
        fragmentManager = getSupportFragmentManager();
        homeMainFragment = new HomeMainFragment();
        homePlayVideoFragment = new HomePlayVideoFragment();
        homeMyPlaylistFragment = new HomeMyPlaylistFragment();

        replaceHomeFragment();
    }

    public static String addVideoToPlaylist(String videoURL) {
        VideoInPlaylist videoInPlaylist = userPlaylistDao.getSavedVideo(currentUserID, videoURL);
        if (videoInPlaylist != null) {
            return "Video already in your playlist!";
        }
        userPlaylistDao.addVideoToPlaylist(new VideoInPlaylist(currentUserID, videoURL));
        return "Add video successful";
    }

    public void logout() {
        finish();
    }

    // Replacing fragment methods
    public void replaceHomeFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction = fragmentTransaction.replace(R.id.fragmentContainerView, homeMainFragment);
        fragmentTransaction.commit();
    }

    public void replaceHomeFragment(Bundle bundle) {
        homeMainFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction = fragmentTransaction.replace(R.id.fragmentContainerView, homeMainFragment);
        fragmentTransaction.commit();
    }

    public void replacePlayVideoFragment(Bundle bundle) {
        homePlayVideoFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction = fragmentTransaction.replace(R.id.fragmentContainerView, homePlayVideoFragment);
        fragmentTransaction.commit();
    }

    public void replacePlaylistFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("UserID", currentUserID);
        homeMyPlaylistFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction = fragmentTransaction.replace(R.id.fragmentContainerView, homeMyPlaylistFragment);
        fragmentTransaction.commit();
    }
}