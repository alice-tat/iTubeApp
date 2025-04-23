package deakin.sit.itubeapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserPlaylistDao {
    @Query("SELECT * FROM VideoInPlaylist WHERE userID = :userID")
    List<VideoInPlaylist> getAllSavedVideos(int userID);

    @Query("SELECT * FROM VideoInPlaylist WHERE userID = :userID AND videoURL = :url")
    VideoInPlaylist getSavedVideo(int userID, String url);

    @Insert
    void addVideoToPlaylist(VideoInPlaylist videoInPlaylist);
}
