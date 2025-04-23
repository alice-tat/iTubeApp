package deakin.sit.itubeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeMainFragment extends Fragment {
    private EditText inputYoutubeURL;
    private Button playButton, addToPlayListButton, myPlayListButton, logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_main, container, false);

        // Initialize
        inputYoutubeURL = view.findViewById(R.id.inputYoutubeURL);
        playButton = view.findViewById(R.id.playButton);
        addToPlayListButton = view.findViewById(R.id.addToPlayListButton);
        myPlayListButton = view.findViewById(R.id.myPlayListButton);
        logoutButton = view.findViewById(R.id.logoutButton);

        // Setup
        playButton.setOnClickListener(this::handlePlayButton);
        addToPlayListButton.setOnClickListener(this::handleAddVideoButton);
        myPlayListButton.setOnClickListener(this::handleMyPlaylistButton);
        logoutButton.setOnClickListener(this::handleLogoutButton);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Bundle received from playlist fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            String url = bundle.getString("VideoURL", "");
            inputYoutubeURL.setText(url);
        }
    }

    private String getURL() {
        String url = inputYoutubeURL.getText().toString();
        if (url.isEmpty()) {
            Toast.makeText(getContext(), "Please input proper url", Toast.LENGTH_SHORT).show();
            return "";
        } else if (!url.contains("youtube.com") || !url.contains("watch?v=")){
            Toast.makeText(getContext(), "Input value is not a youtube url", Toast.LENGTH_SHORT).show();
            return "";
        }
        return url;
    }

    private void handlePlayButton(View view) {
        String url = getURL();
        if (!url.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString("YoutubeVideoURL", url);
            ((HomeActivity) getActivity()).replacePlayVideoFragment(bundle);
        }
    }

    private void handleAddVideoButton(View view) {
        String url = getURL();
        if (!url.isEmpty()) {
            String result = ((HomeActivity) getActivity()).addVideoToPlaylist(url);
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
    private void handleMyPlaylistButton(View view) {
        ((HomeActivity) getActivity()).replacePlaylistFragment();
    }

    private void handleLogoutButton(View view) {
        ((HomeActivity) getActivity()).logout();
    }
}