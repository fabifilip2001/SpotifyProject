package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Album extends AudioCollection {
    private final Integer releaseYear;
    private final String description;
    private final List<Song> songs;

    public Album(String name, String owner, Integer releaseYear,
                 String description, List<Song> songs) {
        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }

    @Override
    public boolean matchesDescription(String description) {
        return this.description.toLowerCase().startsWith(description.toLowerCase());
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(int index) {
        return songs.get(index);
    }

    @Override
    public String likedContentPagePrint() {
        return null;
    }

    @Override
    public int countLikes() {
        return 0;
    }

    @Override
    public String toString() {
        return getName();
    }
}
