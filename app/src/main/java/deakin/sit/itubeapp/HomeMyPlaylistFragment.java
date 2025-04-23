package deakin.sit.itubeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeMyPlaylistFragment extends Fragment {
    private UserPlaylistDao userPlaylistDao;
    private RecyclerView playlistRecyclerView;
    private PlaylistAdapter playlistAdapter;

    private Button backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_my_playlist, container, false);

        userPlaylistDao = AppDatabase.getInstance(getContext()).userPlaylistDao();
        int userID = getArguments().getInt("UserID", -1);

        backButton = view.findViewById(R.id.backButton);
        playlistRecyclerView = view.findViewById(R.id.playlistRecyclerView);

        playlistAdapter = new PlaylistAdapter(getContext(), this, userPlaylistDao.getAllSavedVideos(userID));
        playlistRecyclerView.setAdapter(playlistAdapter);
        playlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        backButton.setOnClickListener(v -> {
            ((HomeActivity) getActivity()).replaceHomeFragment();
        });

        return view;
    }

    public void selectVideoFromPlaylist(Bundle bundle) {
        ((HomeActivity) getActivity()).replaceHomeFragment(bundle);
    }
}