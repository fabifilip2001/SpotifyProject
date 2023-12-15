package app.page.system.userPages;

import app.audio.Collections.AudioCollection;
import app.audio.Files.Song;
import app.user.User;

import java.util.List;

public final class LikedContentPage extends UserPage {

    public LikedContentPage(final User owner, final List<AudioCollection> audioCollection,
                            final List<Song> likedSongs) {
        super(owner, audioCollection, likedSongs);
    }

    /**
     * function that returns homePage output format
     * */
    @Override
    public String print() {
        return "Liked songs:\n\t"
                +
                getLikedSongs()
                +
                "\n\nFollowed playlists:\n\t"
                +
                getAudioCollection();
    }
}
