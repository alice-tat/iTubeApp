package deakin.sit.itubeapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id = :id")
    User getUserByID(int id);

    @Query("SELECT * FROM user WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Insert
    void register(User user);
}
