package app.page.system.userPages;

import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.page.system.Page;
import app.user.User;
import lombok.Getter;

import java.util.List;

public class UserPage extends Page {
    @Getter
    private List<Song> likedSongs;

    public UserPage(User owner, List<AudioCollection> audioCollection, List<Song> likedSongs) {
        super(owner, audioCollection);
        this.likedSongs = likedSongs;
    }

    @Override
    public void updateLikedSongs(List<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }
}
