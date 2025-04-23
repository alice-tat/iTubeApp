package deakin.sit.itubeapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class HomePlayVideoFragment extends Fragment {
    private Button backButton;
    private TextView videoURL;
    private YouTubePlayerView youTubePlayerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_play_video, container, false);

        // Init
        backButton = view.findViewById(R.id.backButton);
        videoURL = view.findViewById(R.id.videoURL);
        youTubePlayerView = view.findViewById(R.id.youTubePlayerView);

        // Get data
        String url = getArguments().getString("YoutubeVideoURL", "");

        // Setup
        backButton.setOnClickListener(this::replaceHomeFragment);

        videoURL.setText(url);

        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);

                DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);
                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());

                // https://www.youtube.com/watch?v=hLbWbFipb-0
                // https://www.youtube.com/watch?v=_-mx27fNCLM
                String videoId = extractVideoID(url);
                if (!videoId.isEmpty()) {
                    youTubePlayer.loadVideo(videoId, 0);
                }
            }
        });

        return view;
    }

    private void replaceHomeFragment(View view) {
        ((HomeActivity) getActivity()).replaceHomeFragment();
    }

    private String extractVideoID(String url) {
        String substring = url.substring(url.indexOf("watch?v="));
        return substring.split("=")[1];
    }
}