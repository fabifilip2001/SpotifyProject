package app.page.system.userPages;

import app.audio.Collections.AudioCollection;
import app.audio.Files.Song;
import app.page.system.PageUtils;
import app.user.User;

import java.util.List;

public final class HomePage extends UserPage {
    public HomePage(final User owner, final List<AudioCollection> audioCollection,
                    final List<Song> likedSongs) {
        super(owner, PageUtils.homePagePlaylists(audioCollection),
                PageUtils.homePageSongs(likedSongs));
    }

    /**
     * function that returns homePage output format
     * */
    public String print() {
        return "Liked songs:\n\t"
                +
                PageUtils.songsLikedContentPagePrint(getLikedSongs())
                +
                "\n\nFollowed playlists:\n\t"
                +
                PageUtils.collectionLikedContentPagePrint(getAudioCollection());
    }
}
