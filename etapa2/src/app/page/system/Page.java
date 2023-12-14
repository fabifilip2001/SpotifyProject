package app.page.system;

import app.audio.Collections.AudioCollection;
import app.audio.Files.Song;
import app.page.system.hostPages.utils.Announcement;
import app.user.User;
import lombok.Getter;

import java.util.List;

public class Page {
    @Getter
    private final User owner;
    @Getter
    private List<AudioCollection> audioCollection;

    public Page(User owner, List<AudioCollection> audioCollection) {
        this.owner = owner;
        this.audioCollection = audioCollection;
    }

    public void updateLikedSongs(List<Song> likedSongs) { }

    public void updateAudioCollection(List<AudioCollection> audioCollection) {
        this.audioCollection = audioCollection;
    }

    public void updateAnnouncements(List<Announcement> announcements) { }
    public String print() { return null; };
}
