package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Playlist extends AudioCollection {
    private final ArrayList<Song> songs;
    private Enums.Visibility visibility;
    private Integer followers;
    private int timestamp;

    public Playlist(final String name, final String owner) {
        this(name, owner, 0);
    }

    public Playlist(final String name, final String owner, final int timestamp) {
        super(name, owner);
        this.songs = new ArrayList<>();
        this.visibility = Enums.Visibility.PUBLIC;
        this.followers = 0;
        this.timestamp = timestamp;
    }

    /**
     * auxiliary function that checks if a song is contained by playlist
     * */
    public boolean containsSong(final Song song) {
        return songs.contains(song);
    }

    /**
     * auxiliary function that adds a song to the playlist
     * */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * auxiliary function that removes a song from the playlist
     * */
    public void removeSong(final Song song) {
        songs.remove(song);
    }

    /**
     * auxiliary function that changes the playlist visibility
     * */
    public void switchVisibility() {
        if (visibility == Enums.Visibility.PUBLIC) {
            visibility = Enums.Visibility.PRIVATE;
        } else {
            visibility = Enums.Visibility.PUBLIC;
        }
    }

    /**
     * function that increases playlists followers
     * */
    public void increaseFollowers() {
        followers++;
    }

    /**
     * function that decreases playlists followers
     * */
    public void decreaseFollowers() {
        followers--;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    @Override
    public boolean isVisibleToUser(final String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC
                ||
                (this.getVisibility() == Enums.Visibility.PRIVATE && this.getOwner().equals(user));
    }

    @Override
    public boolean matchesFollowers(final String followersInput) {
        return filterByFollowersCount(this.getFollowers(), followersInput);
    }

    private static boolean filterByFollowersCount(final int count, final String query) {
        if (query.startsWith("<")) {
            return count < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return count > Integer.parseInt(query.substring(1));
        } else {
            return count == Integer.parseInt(query);
        }
    }

    /**
     * function that returns playlists total likes (of the songs)
     * */
    public int countLikes() {
        int countLikes = 0;
        for (Song song : songs) {
            countLikes += song.getLikes();
        }
        return countLikes;
    }

    @Override
    public String likedContentPagePrint() {
        return getName();
    }

    @Override
    public String toString() {
        return getName()
                +
                " - "
                +
                getOwner();
    }

}
