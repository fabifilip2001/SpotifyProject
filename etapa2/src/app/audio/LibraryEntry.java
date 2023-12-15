package app.audio;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class LibraryEntry {
    private final String name;

    /**
     * {@inheritDoc}
     * */
    public LibraryEntry(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     * */
    public boolean matchesName(final String inputName) {
        return getName().toLowerCase().startsWith(inputName.toLowerCase());
    }
    /**
     * {@inheritDoc}
     * */
    public boolean matchesAlbum(final String album) {
        return false;
    }
    /**
     * {@inheritDoc}
     * */
    public boolean matchesTags(final ArrayList<String> tags) {
        return false;
    }
    /**
     * {@inheritDoc}
     * */
    public boolean matchesLyrics(final String lyrics) {
        return false;
    }
    /**
     * {@inheritDoc}
     * */
    public boolean matchesGenre(final String genre) {
        return false;
    }
    /**
     * {@inheritDoc}
     * */
    public boolean matchesArtist(final String artist) {
        return false;
    }
    /**
     * {@inheritDoc}
     * */
    public boolean matchesReleaseYear(final String releaseYear) {
        return false;
    }
    /**
     * {@inheritDoc}
     * */
    public boolean matchesOwner(final String user) {
        return false;
    }
    /**
     * {@inheritDoc}
     * */
    public boolean isVisibleToUser(final String user) {
        return false;
    }
    /**
     * {@inheritDoc}
     * */
    public boolean matchesFollowers(final String followers) {
        return false;
    }
    /**
     * {@inheritDoc}
     * */
    public boolean matchesDescription(final String description) {
        return false;
    }
}
