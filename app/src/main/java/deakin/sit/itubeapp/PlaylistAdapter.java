package deakin.sit.itubeapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    Context context;
    HomeMyPlaylistFragment fragment;

    List<VideoInPlaylist> playlist;

    public PlaylistAdapter(Context context, HomeMyPlaylistFragment fragment, List<VideoInPlaylist> videoInPlaylists) {
        this.context = context;
        this.fragment = fragment;
        this.playlist = videoInPlaylists;
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.ViewHolder holder, int position) {
        VideoInPlaylist videoInPlaylist = playlist.get(position);

        String videoURL = videoInPlaylist.getVideoURL();

        holder.url.setText(videoURL);
        holder.viewButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("VideoURL", videoURL);
            fragment.selectVideoFromPlaylist(bundle);
        });
    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView url;
        Button viewButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            url = itemView.findViewById(R.id.url);
            viewButton = itemView.findViewById(R.id.viewButton);
        }
    }
}
