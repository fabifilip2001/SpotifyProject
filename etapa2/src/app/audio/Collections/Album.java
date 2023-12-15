package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.List;

@Getter
public final class Album extends AudioCollection {
    private final Integer releaseYear;
    private final String description;
    private final List<Song> songs;
    private int timestamp;

    public Album(final String name, final String owner, final Integer releaseYear,
                 final String description, final List<Song> songs, final int timestamp) {
        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
        this.timestamp = timestamp;
    }

    @Override
    public boolean matchesDescription(final String descriptionInput) {
        return this.description.toLowerCase().startsWith(descriptionInput.toLowerCase());
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
    public String likedContentPagePrint() {
        return null;
    }

    @Override
    public int countLikes() {
        int count = 0;
        for (Song song : songs) {
            count += song.getLikes();
        }
        return count;
    }

    @Override
    public String toString() {
        return getName();
    }
}
