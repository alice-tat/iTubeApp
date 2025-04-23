package deakin.sit.itubeapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"userID", "videoURL"})
public class VideoInPlaylist {
    @ColumnInfo(name = "userID")
    private int userID;

    @ColumnInfo(name = "videoURL") @NonNull
    private String videoURL;

    public VideoInPlaylist(int userID, String videoURL) {
        this.userID = userID;
        this.videoURL = videoURL;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
