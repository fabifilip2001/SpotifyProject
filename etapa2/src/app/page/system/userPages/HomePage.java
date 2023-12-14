package app.page.system.userPages;

import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.page.system.Page;
import app.page.system.PageUtils;
import app.user.User;

import java.util.List;

public class HomePage extends UserPage {
    public HomePage(User owner, List<AudioCollection> audioCollection, List<Song> likedSongs) {
        super(owner, audioCollection, likedSongs);
    }

    public String print() {
        return "Liked songs:\n\t"
                +
                getLikedSongs()
                +
                "\n\nFollowed playlists:\n\t"
                +
                getAudioCollection()
                ;
    }
}
