package app.page.system.userPages;

import app.audio.Collections.AudioCollection;
import app.audio.Files.Song;
import app.page.system.Page;
import app.user.User;
import lombok.Getter;

import java.util.List;

public class UserPage extends Page {
    @Getter
    private List<Song> likedSongs;

    public UserPage(final User owner, final List<AudioCollection> audioCollection,
                    final List<Song> likedSongs) {
        super(owner, audioCollection);
        this.likedSongs = likedSongs;
    }

    /**
     * function that updates likedSongs from an user page
     * */
    @Override
    public void updateLikedSongs(final List<Song> newLikedSongs) {
        this.likedSongs = newLikedSongs;
    }
}
