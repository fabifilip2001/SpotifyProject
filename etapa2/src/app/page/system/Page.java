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

    public Page(final User owner, final List<AudioCollection> audioCollection) {
        this.owner = owner;
        this.audioCollection = audioCollection;
    }

    /**
     * method that will be overriden by subclasses;
     * it updates the likedSongs from an userPage
     * */
    public void updateLikedSongs(final List<Song> likedSongs) { }

    /**
     * method that will be overriden by subclasses;
     * it updates the audioCollection from an userPage
     * */
    public void updateAudioCollection(final List<AudioCollection> newAudioCollection) {
        this.audioCollection = newAudioCollection;
    }

    /**
     * method that will be overriden by subclasses;
     * it updates the announcements from an artistPage
     * */
    public void updateAnnouncements(final List<Announcement> announcements) { }

    /**
     * method that will be overriden by subclasses;
     * it prints every page in its special format
     * */
    public String print() {
        return null;
    }
}
